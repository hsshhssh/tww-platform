package com.xqh.tww.service;

import com.riversoft.weixin.pay.payment.Payments;
import com.riversoft.weixin.pay.payment.bean.UnifiedOrderRequest;
import com.riversoft.weixin.pay.payment.bean.UnifiedOrderResponse;
import com.xqh.tww.entity.dto.PayOrderDTO;
import com.xqh.tww.tkmybatis.entity.TwwDoll;
import com.xqh.tww.tkmybatis.entity.TwwOrder;
import com.xqh.tww.tkmybatis.entity.TwwOrderPay;
import com.xqh.tww.tkmybatis.entity.TwwUser;
import com.xqh.tww.tkmybatis.mapper.TwwOrderMapper;
import com.xqh.tww.tkmybatis.mapper.TwwOrderPayMapper;
import com.xqh.tww.utils.NoUtils;
import com.xqh.tww.utils.common.ExampleBuilder;
import com.xqh.tww.utils.common.Search;
import com.xqh.tww.utils.config.CommonConfig;
import com.xqh.tww.utils.en.OrderStatusEnum;
import com.xqh.tww.utils.en.PayStatusEnum;
import com.xqh.tww.utils.en.PreventRepeatEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hssh on 2017/12/24.
 */
@Service
public class PayService
{
    private static Logger logger = LoggerFactory.getLogger(PayService.class);

    @Resource
    private CommonConfig commonConfig;
    @Resource
    private TwwOrderPayMapper payMapper;
    @Resource
    private TwwOrderMapper orderMapper;
    @Resource
    private PreventRepeatService repeatService;
    @Resource
    private OrderService orderService;

    public TwwOrderPay buildOrderPay(TwwOrder order, TwwDoll doll, PayOrderDTO dto, TwwUser user)
    {
        long nowTime = System.currentTimeMillis();

        TwwOrderPay pay = new TwwOrderPay();
        pay.setPayNo(NoUtils.getPayNo(user.getId()));
        pay.setPayUserId(user.getId());
        pay.setPayUserName(user.getName());
        pay.setPayUserAvatar(user.getAvatar());
        pay.setPayAmount(dto.getAmount());
        pay.setPayStatus(PayStatusEnum.INIT.getCode());
        pay.setUserId(order.getUserId());
        pay.setOrderId(order.getId());
        pay.setOrderNo(order.getOrderNo());
        pay.setDollId(doll.getId());
        pay.setDollName(doll.getName());
        pay.setCreateTime(nowTime);
        pay.setUpdateTime(nowTime);

        return pay;
    }

    public UnifiedOrderResponse getPayInfo(TwwDoll doll, TwwUser payUser, TwwOrderPay pay, String ip)
    {
        UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
        unifiedOrderRequest.setBody(doll.getName());
        unifiedOrderRequest.setDetail(doll.getName());
        unifiedOrderRequest.setTradeNumber(String.valueOf(pay.getPayNo()));
        unifiedOrderRequest.setTotalFee(pay.getPayAmount().intValue());
        unifiedOrderRequest.setBillCreatedIp(ip);
        unifiedOrderRequest.setNotifyUrl(commonConfig.getHost() + "/xqh/wawa/tww/wx/pay/callback");
        unifiedOrderRequest.setTradeType("JSAPI");
        unifiedOrderRequest.setOpenId(payUser.getOpenId());

        UnifiedOrderResponse response = Payments.defaultPayments().unifiedOrder(unifiedOrderRequest);

        return response;
    }

    @Transactional
    public void dealNotify(String payNo, String wxOrderNo)
    {
        long nowTime = System.currentTimeMillis();

        // 修改支付状态
        Search search = new Search();
        search.put("payNo_eq", payNo);
        Example example = new ExampleBuilder(TwwOrderPay.class).search(search).build();
        List<TwwOrderPay> payList = payMapper.selectByExample(example);
        if(payList.size() != 1)
        {
            logger.error("支付回调 payNo无效：{}", payNo);
            throw new RuntimeException("订单回到 payNo无效");
        }
        TwwOrderPay pay = payList.get(0);
        TwwOrderPay newPay = new TwwOrderPay();
        newPay.setId(pay.getId());
        newPay.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        newPay.setThridOrderNo(wxOrderNo);
        newPay.setUpdateTime(nowTime);
        payMapper.updateByPrimaryKeySelective(newPay);

        // 增加订单已付金额
        TwwOrder order = orderMapper.selectByPrimaryKeyLock(pay.getOrderId());
        if(null == order)
        {
            logger.error("支付回调 订单异常 orderId:{}", pay.getOrderId());
            throw new RuntimeException("支付回调 订单异常");
        }
        TwwOrder newOrder = new TwwOrder();
        newOrder.setId(pay.getOrderId());
        long currentAmount = order.getCurrentAmount() + pay.getPayAmount();
        newOrder.setCurrentAmount(currentAmount);
        if(currentAmount >= order.getDollAmount())
        {
            logger.info("支付回调 讨娃娃成功orderId:{}", order.getId());
            newOrder.setStatus(OrderStatusEnum.SUCCESS.getCode());
            repeatService.removeRepeatFlag(PreventRepeatEnum.ORDER.getCode(), orderService.getOrderRepeatFlag(pay.getUserId(), pay.getOrderId()));
        }
        newOrder.setUpdateTime(nowTime);
        orderMapper.updateByPrimaryKeySelective(newOrder);

        logger.info("支付回调 操作成功");
    }

}
