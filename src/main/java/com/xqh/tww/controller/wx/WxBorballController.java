package com.xqh.tww.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.riversoft.weixin.common.oauth2.AccessToken;
import com.riversoft.weixin.common.oauth2.OpenUser;
import com.riversoft.weixin.open.base.AppSetting;
import com.riversoft.weixin.open.oauth2.OpenOAuth2s;
import com.xqh.tww.entity.vo.GetOpenIdInitVO;
import com.xqh.tww.entity.vo.TwwUserVO;
import com.xqh.tww.service.UserService;
import com.xqh.tww.tkmybatis.entity.TwwUser;
import com.xqh.tww.utils.common.CommonUtils;
import com.xqh.tww.utils.common.DozerUtils;
import com.xqh.tww.utils.common.HttpResult;
import com.xqh.tww.utils.common.HttpUtils;
import com.xqh.tww.utils.config.CommonConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by hssh on 2017/12/31.
 */
@Api("微信相关Borball")
@RequestMapping("/xqh/wawa/tww/wx/borball")
@RestController
@Slf4j
public class WxBorballController
{
    private static OpenOAuth2s openOAuth2s;
    @Resource
    private CommonConfig commonConfig;
    @Resource
    private UserService userService;

    @PostConstruct
    public void initOpenOAuth2s() {
        AppSetting appSetting = new AppSetting(commonConfig.getAppId().trim(), commonConfig.getAppSecret().trim());
        openOAuth2s = OpenOAuth2s.with(appSetting);
    }

    @GetMapping("getOpenId")
    @ApiOperation("获取openId初始化接口")
    public void getOpenIdInit(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        log.info(" WxBorballController 获取openId初始化接口......");
        String redirectUrl = req.getParameter("url");
        log.info("redirectUrl:{}", redirectUrl);

        String url = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STATE#wechat_redirect", commonConfig.getAppId().trim(), redirectUrl, commonConfig.getScope().trim());
        GetOpenIdInitVO vo = new GetOpenIdInitVO();
        vo.setUrl(url);
        log.info("WxBorballController 获取openId初始化接口 返回值 vo:{}", vo);
        //return vo;

        resp.sendRedirect(url);
    }

    @GetMapping("getInfo")
    @ApiOperation("获取微信用户信息接口")
    public TwwUserVO getInfo(HttpServletRequest req, HttpServletResponse resp)
    {
        TreeMap<String, String> params = CommonUtils.getParams(req);
        log.info("WxBorballController getInfo params:{}", params);

        String code = req.getParameter("code");
        HttpResult httpResult = HttpUtils.get(String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code"));
        log.info("get access_tokenInfo result:{}", JSONObject.toJSON(httpResult));
        //AccessToken accessToken = openOAuth2s.getAccessToken("code");
        AccessToken accessToken = JSONObject.parseObject(httpResult.getContent(), AccessToken.class);
        log.info("accessToken:{}", accessToken);
        OpenUser user = openOAuth2s.userInfo(accessToken.getAccessToken(), accessToken.getOpenId());
        log.info("OpenUser:{}", user);

        log.info("获取openId成功 插入或者更新信息 openId:{}", user.getOpenId());
        TwwUser twwUser = new TwwUser();
        twwUser.setOpenId(user.getOpenId());
        twwUser.setName(user.getNickName());
        twwUser.setSex(user.getSex().getCode());
        twwUser.setAvatar(user.getHeadImgUrl());
        return DozerUtils.map(userService.insert(twwUser), TwwUserVO.class);
    }


}
