package com.xqh.tww.service;

import com.google.common.collect.Maps;
import com.xqh.tww.entity.dto.PayOrderDTO;
import com.xqh.tww.tkmybatis.entity.TwwDoll;
import com.xqh.tww.tkmybatis.entity.TwwOrder;
import com.xqh.tww.tkmybatis.entity.TwwOrderPay;
import com.xqh.tww.tkmybatis.entity.TwwUser;
import com.xqh.tww.utils.NoUtils;
import com.xqh.tww.utils.common.CommonUtils;
import com.xqh.tww.utils.config.CommonConfig;
import com.xqh.tww.utils.en.PayStatusEnum;
import com.xqh.tww.utils.en.PayTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by hssh on 2017/12/24.
 */
@Service
public class PayService
{
    private static Logger logger = LoggerFactory.getLogger(PayService.class);

    @Resource
    private CommonConfig commonConfig;

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

    public String getPayUrl(long payId, long amount, String openId)
    {
        Map<String, String> params = Maps.newHashMap();
        int time = (int) (System.currentTimeMillis()/1000);
        params.put("payUserId", commonConfig.getPayUserId().trim());
        params.put("appId", commonConfig.getPayAppId().trim());
        params.put("money", String.valueOf(amount));
        params.put("time", String.valueOf(time));
        params.put("payType", String.valueOf(PayTypeEnum.WXGZH.getCode()));
        params.put("userOrderNo", String.valueOf(payId));
        params.put("openId", openId);
        params.put("userParam", "0");

        String sign = CommonUtils.getMd5(commonConfig.getPayUserId().trim()
                + commonConfig.getPayAppId().trim()
                + String.valueOf(amount)
                + String.valueOf(time)
                + commonConfig.getPayKey().trim());
        params.put("sign", sign);

        String payUrl = CommonUtils.getFullUrl(commonConfig.getHost().trim(), params);
        logger.info("openId:{} payId:{} payUrl:{}", openId, payId, payUrl);
        return payUrl;
    }

}
