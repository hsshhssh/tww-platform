package com.xqh.tww.utils.wx.pay.model.wxconfig;

import java.util.Properties;

public class Weixin_APP extends PayConfig{

	private static Properties urlsProp;
	
    public static  String appId;
	
    public static  String mchId;
	
    public static  String weChatPaykey;
	
    public static  String appSecret;
    
    public static String apiclienUrl;
	
	
	public Weixin_APP(){
		if (urlsProp==null) {
			
			urlsProp=getProperties("/properties/weixin.properties", urlsProp);
			appId=urlsProp.getProperty("appId_APP");
			mchId=urlsProp.getProperty("mchId_APP");
			weChatPaykey=urlsProp.getProperty("weChatPaykey_APP");
			appSecret=urlsProp.getProperty("appSecret_APP");
			apiclienUrl=urlsProp.getProperty("apiclien_url_APP");
		}
	}


	@Override
	public  String getAppId() {
		
		return appId;
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
	public  String getAppSecret() {
	
		return appSecret;
	}


	@Override
	public String getApiclienUrl() {
		return apiclienUrl;
	}



	
}
