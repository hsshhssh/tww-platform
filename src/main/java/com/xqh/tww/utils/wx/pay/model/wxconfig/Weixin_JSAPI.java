package com.xqh.tww.utils.wx.pay.model.wxconfig;

import java.util.Properties;

public class Weixin_JSAPI extends PayConfig{

	private static Properties urlsProp;
	
    public static  String appId;
	
    public static  String mchId;
	
    public static  String weChatPaykey;
	
    public static  String appSecret;
    
    public static String apiclienUrl;
	
	
	public Weixin_JSAPI() 
	{
		if (urlsProp==null) {
			
			urlsProp=getProperties("/properties/weixin.properties", urlsProp);
			appId=urlsProp.getProperty("appId_JSAPI");
			mchId=urlsProp.getProperty("mchId_JSAPI");
			weChatPaykey=urlsProp.getProperty("weChatPaykey_JSAPI");
			appSecret=urlsProp.getProperty("appSecret_JSAPI");
			apiclienUrl=urlsProp.getProperty("apiclien_url_JSAPI");
		}
	}


	
	@Override
	public String getAppId() {
	
	
		return 	appId;
	}


	@Override
	public String getMchId() {
		
		return mchId;
	}


	@Override
	public String getWeChatPaykey() {
		
		return weChatPaykey;
	}


	@Override
	public String getAppSecret() {
		
		return appSecret;
	}


	@Override
	public String getApiclienUrl() {
		return apiclienUrl;
	}
	
}
