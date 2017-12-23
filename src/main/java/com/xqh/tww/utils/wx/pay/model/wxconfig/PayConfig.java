package com.xqh.tww.utils.wx.pay.model.wxconfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract  class  PayConfig {

	
	public abstract  String  getAppId();
	
	public abstract String getMchId();
	
	public abstract String getWeChatPaykey();
	
	public abstract String getAppSecret();
	
	public abstract String getApiclienUrl();
	
	
	
	
	public  Properties getProperties(String url,Properties urlsProp){
		
			 InputStream in = PayConfig.class.getClass().getResourceAsStream(url);

			 urlsProp = new Properties(); 	
		 
			  try {
				urlsProp.load(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
			  
		return urlsProp;
	}
	
	
	
	
	
	
}
