package com.xqh.tww.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.riversoft.weixin.pay.payment.Payments;
import com.riversoft.weixin.pay.payment.bean.UnifiedOrderRequest;
import com.riversoft.weixin.pay.payment.bean.UnifiedOrderResponse;
import com.xqh.tww.entity.vo.TwwUserVO;
import com.xqh.tww.service.UserService;
import com.xqh.tww.tkmybatis.entity.TwwUser;
import com.xqh.tww.utils.common.CommonUtils;
import com.xqh.tww.utils.common.DozerUtils;
import com.xqh.tww.utils.common.ErrorResponseEunm;
import com.xqh.tww.utils.config.CommonConfig;
import com.xqh.tww.utils.wx.auth.WXAuthorizationCode;
import com.xqh.tww.utils.wx.auth.WXOauth2;
import com.xqh.tww.utils.wx.auth.WXUserInfo;
import com.xqh.tww.utils.wx.notify.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.xqh.tww.utils.wx.notify.NotifyConstant.WX_RESPONSE_FAIL;
import static com.xqh.tww.utils.wx.notify.NotifyConstant.WX_RESPONSE_SUCCESS;


/**
 * Created by hssh on 2017/12/23.
 */
@RequestMapping("/xqh/wawa/tww/wx")
@RestController
public class WxController
{
    private static Logger logger = LoggerFactory.getLogger(WxController.class);

    @Resource
    private UserService userService;
    @Resource
    private CommonConfig commonConfig;

    @GetMapping("getOpenId")
    public void getOpenInit(HttpServletRequest req, HttpServletResponse resp)
    {
        logger.info("进入getOpenId.......");

        try
        {
            String redirectUrl = URLEncoder.encode(commonConfig.getHost().trim() +  "/xqh/wawa/tww/wx/getCode", "utf8");
            String authorizeUrl = WXOauth2.authorize(redirectUrl);
            resp.sendRedirect(authorizeUrl);
        } catch (IOException e)
        {
            logger.error("跳转失败 {}", Throwables.getStackTraceAsString(e));
        }
    }

    @GetMapping("getCode")
    public TwwUserVO getCode(HttpServletRequest req, HttpServletResponse resp)
    {
        TreeMap<String, String> params = CommonUtils.getParams(req);
        for (String s : params.keySet())
        {
            logger.info("微信回调参数 {} ： {}", s, params.get(s));
        }

        String code = params.get("code");

        WXAuthorizationCode wxAuthorizationCode = null;
        WXUserInfo wxUserInfo = null;
        try
        {
            wxAuthorizationCode = WXOauth2.wxOpenid(code);
            wxUserInfo = WXOauth2.wxUserInfo(wxAuthorizationCode.getAccess_token(), wxAuthorizationCode.getOpenid());

            logger.info("openId信息: {}", JSONObject.toJSON(wxAuthorizationCode));
            logger.info("用户信息：{}", JSONObject.toJSON((wxUserInfo)));
        } catch (InterruptedException e)
        {
            logger.error("获取openId失败 {}", e);
            CommonUtils.sendError(resp, ErrorResponseEunm.ERROR_GET_OPENID);
            return null;
        }

        if(StringUtils.isBlank(wxUserInfo.getOpenid()))
        {
            logger.error("获取openId为空 wxUserInfo:{}", JSONObject.toJSON(wxUserInfo));
            CommonUtils.sendError(resp, ErrorResponseEunm.ERROR_GET_OPENID);
            return null;
        }

        logger.info("获取openId成功 插入或者更新信息 openId:{}", wxUserInfo.getOpenid());
        TwwUser twwUser = new TwwUser();
        twwUser.setOpenId(wxUserInfo.getOpenid());
        twwUser.setName(wxUserInfo.getNickname());
        twwUser.setSex(Integer.parseInt(wxUserInfo.getSex()));
        twwUser.setAvatar(wxUserInfo.getHeadimgurl());
        return DozerUtils.map(userService.insert(twwUser), TwwUserVO.class);
    }

    @GetMapping("shareUrl")
    public void shareUrl(HttpServletRequest req, HttpServletResponse resp)
    {
        //WechatUtil.setWechatJsConfig2()
        return ;
    }

    @GetMapping("/pay")
    public void pay()
    {
        UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
        unifiedOrderRequest.setBody("牛奶 小号");
        unifiedOrderRequest.setDetail("小号牛奶 鲜奶");
        unifiedOrderRequest.setTradeNumber("1292063901201605160012300016");
        unifiedOrderRequest.setTotalFee(100);
        unifiedOrderRequest.setBillCreatedIp("192.168.1.103");
        unifiedOrderRequest.setNotifyUrl("http://gzriver.com/order/pay/notify");
        unifiedOrderRequest.setTradeType("JSAPI");
        unifiedOrderRequest.setOpenId("oELhlt7Q-lRmLbRsPsaKeVX6pqjg");

        UnifiedOrderResponse response = Payments.defaultPayments().unifiedOrder(unifiedOrderRequest);
        logger.info("pay response:{}", response);
    }

    @PostMapping("/pay/callback")
    public void payCallback(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String notifyXml = getRequestBody(request);

        if(logger.isInfoEnabled()) {
            logger.info("received WeChat notify,notify context is {}", notifyXml);
        }

        PrintWriter out = response.getWriter();

        Trade payResult = buildPayResult(notifyXml);

        if(payResult == null){
            out.write(NotifyConstant.WX_RESPONSE_FAIL);
        } else {
            //payResult.setSource(1);
            //ResultModel processResult = processorManager.process(payResult);
            logger.info("微信公众号支付回调结果：{}", JSONObject.toJSON(payResult));

            payResult.setTradeStatus(PayTask.STATUS_SUCCESS);
            if(payResult.getTradeStatus()== PayTask.STATUS_SUCCESS){
                out.write(WX_RESPONSE_SUCCESS);
            }else {
                out.write(WX_RESPONSE_FAIL);
            }
        }

        out.flush();
        out.close();
    }

    private Trade buildPayResult(String notifyXml){

        SortedMap<String,String> map = XmlHelper.parseXmlToMap(notifyXml);
        if( map == null ){
            logger.warn("can not parse the notify params,the notify xml={}", notifyXml);
            return null;
        }

        // TODO 暂时不验签名
        //String sign = WxPayHelper.createSign(map);
        //if( !sign.equalsIgnoreCase(map.get("sign")) ){
        //    LOGGER.warn("check params error,the notify xml is:" + notifyXml+";the sign of mine is:"+sign);
        //    return null;
        //}

        Trade payResult = new Trade();
        payResult.setPayStatus("SUCCESS".equals(map.get("result_code")) ? PayStatus.STATUS_SUCCESS : PayStatus.STATUS_FAIL);
        payResult.setTradeStatus("SUCCESS".equals(map.get("result_code")) ? PayTask.STATUS_SUCCESS : PayTask.STATUS_FAIL);
        String totalFeeStr = map.get("total_fee");
        payResult.setTotalFee(new BigDecimal(totalFeeStr));
        payResult.setOrderId(map.get("out_trade_no"));
        payResult.setOutTradeId(map.get("transaction_id"));
        payResult.setReturnContent(notifyXml);
        payResult.setResultTime(new Date());

        return payResult;
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String inputLine = reader.readLine();
        while (inputLine != null) {
            sb.append(inputLine);
            inputLine = reader.readLine();
        }
        reader.close();
        return sb.toString();
    }

}
