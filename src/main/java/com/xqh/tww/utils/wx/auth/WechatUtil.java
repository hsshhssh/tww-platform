package com.xqh.tww.utils.wx.auth;

import com.alibaba.fastjson.JSONObject;
import com.xqh.tww.utils.wx.pay.util.MD5Util;
import com.xqh.tww.utils.wx.pay.util.TenpayUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import weixin.popular.util.JsUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Component
public class WechatUtil implements ApplicationListener<ContextRefreshedEvent> {
	private static Logger log = Logger.getLogger(WechatUtil.class);

	// @Value("#{weixinConfig.appid}")
	public static String appid = "wxb9e4c630ffe272e7";

	// @Value("#{weixinConfig.appsecret}")
	public static String appsecret = "107e969f8d112763b931b41379c7183f";
	
	private static String partnerkey = "1399562302";
	
	private static String WechatPayKey="1fadfs8023423jkosdviusovjs234232";

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		try {
		/*	log.info("初始化微信受权");
			log.info("=======================");
			Thread.sleep(5000);
			TokenManager.init(appid, appsecret);
			System.out.println(TokenManager.getToken(appid));
			log.info("=======================");
			//Thread.sleep(10000);
			TicketManager.init(appid, 10, 60 * 119);
			System.out.println(TicketManager.getTicket(appid));*/
		} catch (Exception ex) {
			log.error("网络不可达");
		}
	}

	/*
	 * public static Map<String, Object> setWechatJsConfig( String url, String
	 * params) { if (StringUtils.isNotBlank(params)) { url += "?" + params; }
	 * log.info("url:" + url); String token = TokenManager.getDefaultToken();
	 * log.info("token:" + token); if (StringUtils.isNotBlank(token)) { String
	 * ticket = TicketManager.getTicket(appid); log.info("ticket:" + ticket); if
	 * (StringUtils.isNotBlank(ticket)) { String jsconfig =
	 * JsUtil.generateConfigJson(ticket, false,appid, url,
	 * JsUtil.ALL_JS_API_LIST); // log.info("jsconfig:" + jsconfig);
	 * 
	 * JSONObject jsonObject = JSONObject.fromObject(jsconfig); Map<String,
	 * Object> queryInfListMap = new HashMap<String, Object>();
	 * 
	 * Map map = jsonObject; queryInfListMap.put("jsconfig", jsconfig);
	 * queryInfListMap.put("appId", appid); queryInfListMap.put("timestamp",
	 * map.get("timestamp")); queryInfListMap.put("nonceStr",
	 * map.get("nonceStr")); queryInfListMap.put("signature",
	 * map.get("signature")); return queryInfListMap; } } return null; }
	 */

	public static Map<String, Object> setWechatJsConfig(String url, String params) {
		try {
			if (StringUtils.isNotBlank(params)) {
				url += "?" + params;
			}
			log.info("url:" + url);
			Map<String, String> parameter = new HashMap<>();
			parameter.put("grant_type", "client_credential");
			parameter.put("appid", appid);
			parameter.put("secret", appsecret);
			String tempToken = getJustParameterFromWeiXinApi("access_token", "https://api.weixin.qq.com/cgi-bin/token",
					parameter, null);
			System.out.println(tempToken);
			if (StringUtils.isNotBlank(tempToken)) {
				log.info("url:" + url + " tempToken:" + tempToken);
				// String ticket =
				// TicketManager.getTicket(Global.getConfig("wechatappid"));
				Map<String, String> parameter2 = new HashMap<>();
				parameter2.put("access_token", tempToken);
				parameter2.put("type", "jsapi");
				String ticket = getJustParameterFromWeiXinApi("ticket",
						"https://api.weixin.qq.com/cgi-bin/ticket/getticket", parameter2, null);
				log.info("ticket:" + ticket);
				if (StringUtils.isNotBlank(ticket)) {
					String jsconfig = JsUtil.generateConfigJson(ticket, false, appid, url, JsUtil.ALL_JS_API_LIST);
					// log.info("jsconfig:" + jsconfig);
					JSONObject jsonObject = JSONObject.parseObject(jsconfig);
					Map<String, Object> queryInfListMap = new HashMap<String, Object>();

					Map map = jsonObject;
					queryInfListMap.put("jsconfig", jsconfig);
					queryInfListMap.put("appId", appid);
					queryInfListMap.put("timestamp", map.get("timestamp"));
					queryInfListMap.put("nonceStr", map.get("nonceStr"));
					queryInfListMap.put("signature", map.get("signature"));
					return queryInfListMap;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String,Object> setWechatJsConfig2(String url,String params){
		try {
			if(StringUtils.isNotBlank(params)) {
				url+="?"+params;
			}
			log.info("url:"+url);
			Map<String, String> parameter = new HashMap<>();
			String tempToken = WxQuartzBean.getAccess_token();
			System.out.println(tempToken);
			if(StringUtils.isNotBlank(tempToken)) {
				log.info("url:"+url+"tempToken:"+tempToken);
				String ticket = WxQuartzBean.getTicket();
				log.info("ticket:"+ticket);
				if(StringUtils.isNotBlank(ticket)) {
					String jsconfig = JsUtil.generateConfigJson(ticket, false, appid, url, JsUtil.ALL_JS_API_LIST);
					JSONObject jsonObject = JSONObject.parseObject(jsconfig);
					Map<String,Object> queryInfListMap = new HashMap<String,Object>();
					
					Map map = jsonObject;
					queryInfListMap.put("jsconfig", jsconfig);
					queryInfListMap.put("appId", appid);
					queryInfListMap.put("timestamp", map.get("timestamp"));
					queryInfListMap.put("nonceStr", map.get("nonceStr"));
					queryInfListMap.put("signature", map.get("signature"));
					return queryInfListMap;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////
	///////// 发送微信api请求////////////////////////////////////////////////////////////////////
	/**
	 * 如果parameter为null而jsonData不为null则为post请求，否则为get请求
	 * 根据URL以及参数到微信api获取全部参数结果，失败返回null
	 * 
	 * @param URL
	 * @param parameter
	 * @return
	 */
	public static Map<String, String> getParameterFromWeiXinApi(String URL, Map<String, String> parameter,
			String jsonDate) {
		try {
			String result = null;
			if (parameter != null && parameter.size() > 0) {
				int i = 1;
				parameter.size();
				String parameters = "?";
				for (String key : parameter.keySet()) {
					if (i == parameter.size()) {
						parameters = parameters + key + "=" + parameter.get(key);
					} else {
						parameters = parameters + key + "=" + parameter.get(key) + "&";
						i++;
					}
				}
				URL = URL + parameters;
				result = post(URL, "");
			} else if (jsonDate != null && jsonDate != "") {
				result = post(URL, jsonDate);
			}
			System.out.println(result);
			JSONObject jsonObject = JSONObject.parseObject(result);
			Map<String, String> data = new HashMap<String, String>();
			// 将json字符串转换成jsonObject
			Iterator it = jsonObject.keySet().iterator();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				Object value1 = jsonObject.get(key);
				String value = "";
				if (value1 instanceof String) {
					value = (String) value1;
				} else if (value1 instanceof Integer) {
					Integer value2 = (Integer) value1;
					value = String.valueOf(value2);
				}
				data.put(key, value);
			}
			System.out.println(data.toString());
			return data;
		} catch (Exception e) {
			System.out.println("######获取失败#############################");
			return null;
		}
	}

	/**
	 * 如果parameter为null而jsonData不为null则为post请求，否则为get请求
	 * 根据URL和参数，获取特定的参数结果，失败返回null
	 * 
	 * @param resultParameter
	 * @param URL
	 * @param parameter
	 * @return
	 */
	public static String getJustParameterFromWeiXinApi(String resultParameter, String URL,
			Map<String, String> parameter, String jsonData) {
		Map<String, String> resultMap = getParameterFromWeiXinApi(URL, parameter, jsonData);
		String result = null;
		try {
			if (resultMap != null) {
				result = resultMap.get(resultParameter);
			}
			return result;
		} catch (Exception e) {
			System.out.println("######获取失败#############################");
			return result;
		}
	}

	/**
	 * 发送微信api请求
	 * 
	 * @param strURL
	 * @param jsonData
	 * @return
	 */
	public static String post(String strURL, String jsonData) {
		String result = null;
		try {
			URL url = new URL(strURL); // 创建连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST"); // 设置 POST 请求方式
			OutputStream out = conn.getOutputStream();
			out.write(jsonData.getBytes("UTF-8"));
			out.flush();
			out.close();
			// 读取响应
			BufferedReader buf = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			result = buf.readLine();
			System.out.println("result: " + result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public static String getNonceStr() {
		// 随机数
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}

	public static String createSign(String charSet, SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		String result = null;
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + WechatPayKey);
		System.out.println(sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), charSet).toUpperCase();
		result = sb.toString()+"&sign="+sign;
		System.out.println(result);
		return result;
	}

	public static String getRequestXml(SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
}
