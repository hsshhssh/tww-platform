package com.xqh.tww.service;

import com.riversoft.weixin.pay.payment.Payments;
import com.riversoft.weixin.pay.payment.bean.UnifiedOrderRequest;
import com.riversoft.weixin.pay.payment.bean.UnifiedOrderResponse;
import com.xqh.tww.tkmybatis.entity.TwwDoll;
import com.xqh.tww.tkmybatis.entity.TwwOrderPay;
import com.xqh.tww.tkmybatis.entity.TwwUser;
import com.xqh.tww.utils.config.CommonConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hssh on 2017/12/24.
 */
@Service
public class WxPayService
{
    @Resource
    private CommonConfig commonConfig;

    public UnifiedOrderResponse getPayInfo(TwwDoll doll, TwwUser payUser, TwwOrderPay pay, String ip)
    {
        UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
        unifiedOrderRequest.setBody(doll.getName());
        unifiedOrderRequest.setDetail(doll.getName());
        unifiedOrderRequest.setTradeNumber(String.valueOf(pay.getId()));
        unifiedOrderRequest.setTotalFee(pay.getPayAmount().intValue());
        unifiedOrderRequest.setBillCreatedIp(ip);
        unifiedOrderRequest.setNotifyUrl(commonConfig.getHost() + "/xqh/wawa/tww/order/payOrder/callback");
        unifiedOrderRequest.setTradeType("JSAPI");
        unifiedOrderRequest.setOpenId(payUser.getOpenId());

        UnifiedOrderResponse response = Payments.defaultPayments().unifiedOrder(unifiedOrderRequest);

        return response;
    }


}
