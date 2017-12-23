package com.xqh.tww.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.xqh.tww.utils.common.CommonUtils;
import com.xqh.tww.utils.wx.auth.WXAuthorizationCode;
import com.xqh.tww.utils.wx.auth.WXOauth2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.TreeMap;

/**
 * Created by hssh on 2017/12/23.
 */
@RestController("/xqh/wawa/tww/wx")
public class WxController
{
    private static Logger logger = LoggerFactory.getLogger(WxController.class);

    @GetMapping("getOpenId")
    public void getOpenInit(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            String redirectUrl = URLEncoder.encode("http://wawa.uerbx.com/xqh/wawa/tww/wx/getCode", "utf8");
            String authorizeUrl = WXOauth2.authorize(redirectUrl);
            resp.sendRedirect(authorizeUrl);
        } catch (IOException e)
        {
            logger.error("跳转失败 {}", Throwables.getStackTraceAsString(e));
        }
    }

    @GetMapping("getCode")
    public void getCode(HttpServletRequest req, HttpServletResponse resp)
    {
        TreeMap<String, String> params = CommonUtils.getParams(req);
        for (String s : params.keySet())
        {
            logger.info("微信回调参数 {} ： {}", s, params.get(s));
        }

        String code = params.get("code");

        WXAuthorizationCode wxAuthorizationCode = null;
        try
        {
            wxAuthorizationCode = WXOauth2.wxOpenid(code);
        } catch (InterruptedException e)
        {
            logger.error("获取openId失败 {}", e);
        }

        CommonUtils.writeResponse(resp, JSONObject.toJSON(wxAuthorizationCode));
        return;
    }

}