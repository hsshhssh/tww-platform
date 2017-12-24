package com.xqh.tww.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.xqh.tww.entity.vo.TwwUserVO;
import com.xqh.tww.service.UserService;
import com.xqh.tww.tkmybatis.entity.TwwUser;
import com.xqh.tww.utils.common.CommonUtils;
import com.xqh.tww.utils.common.DozerUtils;
import com.xqh.tww.utils.common.ErrorResponseEunm;
import com.xqh.tww.utils.wx.auth.WXAuthorizationCode;
import com.xqh.tww.utils.wx.auth.WXOauth2;
import com.xqh.tww.utils.wx.auth.WXUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.TreeMap;

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

    @GetMapping("getOpenId")
    public void getOpenInit(HttpServletRequest req, HttpServletResponse resp)
    {
        logger.info("进入getOpenId.......");

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
    }

}
