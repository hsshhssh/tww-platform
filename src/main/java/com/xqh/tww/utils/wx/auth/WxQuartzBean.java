package com.xqh.tww.utils.wx.auth;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时获取微信access_token
 * @author Gunn
 *
 */
public class WxQuartzBean{
	
	private static Logger log = Logger.getLogger(WxQuartzBean.class);
	
	public static String appid = "wxb9e4c630ffe272e7";
	
	public static String appsecret = "107e969f8d112763b931b41379c7183f";
	
	private static String partnerkey = "1399562302";
	
	private static String WechatPayKey="1fadfs8023423jkosdviusovjs234232";
	
	private static String access_token;
	
	private static String ticket;
	
	private static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
	
	private static String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

	//定时获取access_token
	public static void updateAccessTokenAndTicket() {
		log.info("======定时获取微信access_token======");
		Map<String, String> parameter = new HashMap<>();
		parameter.put("grant_type", "client_credential");
		parameter.put("appid", appid);
		parameter.put("secret", appsecret);
		access_token = WechatUtil.getJustParameterFromWeiXinApi("access_token", accessTokenUrl, parameter, null);
		if(access_token==null) {
			log.info("获取access_token失败");
			return;
		}
		
		log.info("--------access_token="+access_token+"----------");
		
		Map<String,String> parameter2 = new HashMap<>();
		parameter2.put("access_token", access_token);
		parameter2.put("type", "jsapi");
		ticket = WechatUtil.getJustParameterFromWeiXinApi("ticket", ticketUrl, parameter2, null);
		
		if(ticket == null) {
			log.info("获取ticket失败");
			return;
		}
		
		log.info("----------ticket="+ticket+"-----------");
		
	}
	
	//手动调用刷新access_token，用于access_token失效时刷新
	public static void updateAccessToken() {
		Map<String, String> parameter = new HashMap<>();
		parameter.put("grant_type", "client_credential");
		parameter.put("appid", appid);
		parameter.put("secret", appsecret);
		access_token = WechatUtil.getJustParameterFromWeiXinApi("access_token", accessTokenUrl, parameter, null);
	}

	public static String getAccess_token() {
		return access_token;
	}

	public static String getTicket() {
		return ticket;
	}

	
}
