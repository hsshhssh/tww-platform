package com.xqh.tww.utils.wx.pay.model.wxconfig;

public class PayConfigInit {

	private static Weixin_APP weixin_APP = new Weixin_APP();

	private static Weixin_JSAPI weixin_JSAPI = new Weixin_JSAPI();

	public static PayConfig init(String type) {
		if ("APP".equals(type)) {
			System.out.println(weixin_APP.getAppId());
			return weixin_APP;

		} else if ("JSAPI".equals(type)) {
			return weixin_JSAPI;
		}
		return null;
	}
}
