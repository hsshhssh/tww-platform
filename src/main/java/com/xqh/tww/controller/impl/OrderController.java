package com.xqh.tww.controller.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.riversoft.weixin.pay.payment.bean.UnifiedOrderResponse;
import com.xqh.tww.controller.api.IOrderController;
import com.xqh.tww.entity.dto.ListDTO;
import com.xqh.tww.entity.dto.PayOrderDTO;
import com.xqh.tww.entity.dto.TwwOrderInsertDTO;
import com.xqh.tww.entity.vo.TwwOrderVO;
import com.xqh.tww.service.OrderService;
import com.xqh.tww.service.PayService;
import com.xqh.tww.service.PreventRepeatService;
import com.xqh.tww.tkmybatis.entity.TwwDoll;
import com.xqh.tww.tkmybatis.entity.TwwOrder;
import com.xqh.tww.tkmybatis.entity.TwwOrderPay;
import com.xqh.tww.tkmybatis.entity.TwwUser;
import com.xqh.tww.tkmybatis.mapper.TwwDollMapper;
import com.xqh.tww.tkmybatis.mapper.TwwOrderMapper;
import com.xqh.tww.tkmybatis.mapper.TwwOrderPayMapper;
import com.xqh.tww.tkmybatis.mapper.TwwUserMapper;
import com.xqh.tww.utils.common.*;
import com.xqh.tww.utils.en.PreventRepeatEnum;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by hssh on 2017/12/19.
 */
@RestController
public class OrderController implements IOrderController
{
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private TwwOrderMapper orderMapper;
    @Resource
    private OrderService orderService;
    @Resource
    private PreventRepeatService preventRepeatService;
    @Resource
    private TwwDollMapper dollMapper;
    @Resource
    private TwwUserMapper userMapper;
    @Resource
    private PayService payService;
    @Resource
    private TwwOrderPayMapper payMapper;

    @Override
    public PageResult<TwwOrderVO> list(@RequestBody @Valid @NotNull ListDTO dto)
    {
        Example example = new ExampleBuilder(TwwOrder.class).search(dto.getSearch()).sort(Arrays.asList("id_desc")).build();
        Page<TwwOrder> twwDollPage = (Page<TwwOrder>) orderMapper.selectByExampleAndRowBounds(example, new RowBounds(dto.getPage(), dto.getSize()));
        return new PageResult<>(twwDollPage.getTotal(), DozerUtils.mapList(twwDollPage.getResult(), TwwOrderVO.class));
    }

    @Override
    public long insertOrder(@RequestBody @Valid @NotNull TwwOrderInsertDTO dto,
                            HttpServletResponse resp)
    {

        // 检验参数
        TwwDoll doll = dollMapper.selectByPrimaryKey(dto.getDollId());
        TwwUser user = userMapper.selectByPrimaryKey(dto.getUserId());
        if(null == doll || null == user)
        {
            logger.error("参数异常 doll:{}为空 或者 user:{}为空 ", doll, user);
            CommonUtils.sendError(resp, ErrorResponseEunm.INVALID_METHOD_ARGS);
            return 0;
        }

        // 防重状态
        boolean isRepeat = preventRepeatService.isRepeat(PreventRepeatEnum.ORDER.getCode(), orderService.getOrderRepeatFlag(dto.getUserId(), dto.getDollId()));
        if(isRepeat)
        {
            logger.error("重复请求 dto:{}", JSONObject.toJSON(dto));
            CommonUtils.sendError(resp, ErrorResponseEunm.REPEAT_ORDER);
            return 0;
        }

        // 新增订单
        TwwOrder order = orderService.buildOrder(doll, user);
        try
        {
            orderMapper.insertSelective(order);
        } catch (Exception e)
        {
            logger.error("新增订单异常 order:{} e:{}", JSONObject.toJSON(order), e);
            CommonUtils.sendError(resp, ErrorResponseEunm.ERROR_CREATE_ORDER);
            return 0;
        }

        return order.getId();

    }

    @Override
    public TwwOrderVO get(@RequestParam("id") long id)
    {
        return null;
    }

    @Override
    public UnifiedOrderResponse payOrder(@RequestBody @Valid @NotNull PayOrderDTO dto,
                                         HttpServletRequest req,
                                         HttpServletResponse resp)
    {
        // 检验参数
        TwwOrder order = orderMapper.selectByPrimaryKey(dto.getOrderId());
        TwwUser user = userMapper.selectByPrimaryKey(dto.getUserId());
        TwwDoll doll = dollMapper.selectByPrimaryKey(dto.getDollId());
        if(null == user
                || null == doll
                || null == order
                || !Objects.equals(order.getDollId(), dto.getDollId()))
        {
            logger.error("参数校验失败 dto:{}", JSONObject.toJSON(dto));
            CommonUtils.sendError(resp, ErrorResponseEunm.INVALID_METHOD_ARGS);
            return null;
        }


        // 新增订单
        TwwOrderPay pay = payService.buildOrderPay(order, doll, dto, user);
        try
        {
            payMapper.insertSelective(pay);
        } catch (Exception e)
        {
            logger.error("新增支付订单失败 pay:{} e:{}", JSONObject.toJSON(pay), e);
            CommonUtils.sendError(resp, ErrorResponseEunm.ERROR_CREATE_PAY_ORDER);
            return null;
        }

        // 返回
        return payService.getPayInfo(doll, user, pay, CommonUtils.getIp(req));
    }

    @Override
    public void payOrderCallback(HttpServletRequest req, HttpServletResponse resp)
    {

    }
}
