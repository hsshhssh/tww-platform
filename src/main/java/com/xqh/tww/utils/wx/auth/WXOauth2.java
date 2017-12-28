package com.xqh.tww.utils.wx.auth;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 网页授权获取微信用户基本信息
 * <p>
 * Author: ocean
 * <p>
 * Date: 2016年1月15日
 * <p>
 * Version: 1.0
 */
@Component
public class WXOauth2 {

	/** 日志 */

	private static String appid = "wxb9e4c630ffe272e7";

	private static String secret = "107e969f8d112763b931b41379c7183f";

	/**
	 * 重定向到微信，用户同意授权，获取code
	 * 
	 * @param request
	 * @param response
	 */
	static public String authorize(String redirectUri) {
		String scope = "snsapi_base"; // 不弹出用户授权界面
		String authorizeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STATE#wechat_redirect";
		authorizeUrl = String.format(authorizeUrl, appid, redirectUri, scope);
		// logger.debug("微信authorizeUrl：{}", authorizeUrl);
		return authorizeUrl;

	}

	/**
	 * 获取openid
	 * 
	 * @param code
	 * @return
	 * @throws InterruptedException
	 */
	static public WXAuthorizationCode wxOpenid(String code) throws InterruptedException {
		String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
		accessTokenUrl = String.format(accessTokenUrl, appid, secret, code);
		String jsonStr = HttpKit.get(accessTokenUrl);
		System.out.println("############################");
		System.out.println(jsonStr);
		// Thread.sleep(5000);
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		WXAuthorizationCode wxCode = new WXAuthorizationCode();
		wxCode.setAccess_token(jsonObject.getString("access_token"));
		wxCode.setOpenid(jsonObject.getString("openid"));
		return wxCode;
	}

	/**
	 * 获取用户头像信息
	 * 
	 * @param accessToken
	 *            此接口access_token使用公众号定时刷新的access_token
	 * @param openid
	 * @return
	 * @throws InterruptedException
	 */
	static public WXUserInfo wxUserInfo(String accessToken, String openid) throws InterruptedException {
		// logger.debug("code：" + code);

		String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
		accessTokenUrl = String.format(accessTokenUrl, accessToken, openid);
		System.out.println("################userInfoUrl:" + accessTokenUrl);
		System.out.println("#####################################1:" + accessToken);
		String jsonStr = HttpKit.get(accessTokenUrl);

		System.out.println(jsonStr);
		// Thread.sleep(5000);
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		String errcode = String.valueOf(jsonObject.get("errcode"));
		System.out.println(errcode);
		if (errcode != null && errcode.equals("40001")) {
			// 当有错误信息时且错误码为40001，刷新access_token
			System.out.println("#####################################刷新access_token");
			WxQuartzBean.updateAccessToken();
			// return wxUserInfo(WxQuartzBean.getAccess_token(), openid);
			accessToken = WxQuartzBean.getAccess_token();
			System.out.println("#####################################2:" + accessToken);
			accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
			accessTokenUrl = String.format(accessTokenUrl, accessToken, openid);
			jsonStr = HttpKit.get(accessTokenUrl);
			System.out.println(jsonStr);
			// Thread.sleep(5000);
			jsonObject = JSON.parseObject(jsonStr);
		}
		if (StringUtils.isBlank(jsonObject.getString("unionid"))) {
			// 当有错误信息时且错误码为40001，刷新access_token
			System.out.println("unionidunionidunionidunionid#####################################刷新access_token");
			WxQuartzBean.updateAccessToken();
			// return wxUserInfo(WxQuartzBean.getAccess_token(), openid);
			accessToken = WxQuartzBean.getAccess_token();
			System.out.println("#####################################3:" + accessToken);
			accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
			accessTokenUrl = String.format(accessTokenUrl, accessToken, openid);
			jsonStr = HttpKit.get(accessTokenUrl);
			System.out.println(jsonStr);
			// Thread.sleep(5000);
			jsonObject = JSON.parseObject(jsonStr);

		}
		WXUserInfo wxUserInfo = new WXUserInfo();
		wxUserInfo.setOpenid(jsonObject.getString("openid"));
		wxUserInfo.setNickname(jsonObject.getString("nickname"));
		wxUserInfo.setSex(jsonObject.getString("sex"));
		wxUserInfo.setProvince(jsonObject.getString("province"));
		wxUserInfo.setCity(jsonObject.getString("city"));
		wxUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
		wxUserInfo.setUnionId(jsonObject.getString("unionid"));
		return wxUserInfo;
	}

}
