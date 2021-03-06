package com.xqh.tww.utils.wx.pay.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;



public class WeChatUtil {

    public static final String ENCODE      = "UTF-8";
    public static final String url         = "";
    public static final String appid       = "";
    public static final String secret      = "";
    public static String       timestamp   = "";
    public static String       signature   = "";
    public static String       nonceStr    = "";
    public static String       jsapiTicket = "";
    public static Date         time        = null;

    // 与接口配置信息中的Token要一致
    private static String      token       = "";

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*String accessToken = getAccessToken();
        String ticket = getJsapiTicket(accessToken);
        Map<String,String> sign = getSign(ticket,true);*/
    }

    public static Map<String, String> creatSign(HttpServletRequest request) {

        String url = request.getRequestURL().toString();
        if (request.getQueryString() != null && !request.getQueryString().equals("")) {
            url += "?" + request.getQueryString();
        }

        if (url.equals(WeChatUtil.url)) {
            url += "/";
        }

        String link = url;
        
        Date time = new Date();

        if (WeChatUtil.time == null) { //超过了一个半小时，或者第一次分享
            WeChatUtil.time = time;
            String accessToken = getAccessToken();
            jsapiTicket = getJsapiTicket(accessToken);
        } else {
            long a = (time.getTime() - WeChatUtil.time.getTime()) / 1000;
            if (a > 3900) {
                WeChatUtil.time = time;
                String accessToken = getAccessToken();
                jsapiTicket = getJsapiTicket(accessToken);
            }
        }

        Map<String, String> sign = getSign(url);
        sign.put("link", link);
        return sign;
    }


    public static Map<String, String> getSign(String url) {

        Map<String, String> ret = new HashMap<String, String>();
        nonceStr = create_nonce_str();
        timestamp = create_timestamp();
        String string1;

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapiTicket +
                  "&noncestr=" + nonceStr +
                  "&timestamp=" + timestamp +
                  "&url=" + url;

        System.out.println(string1);
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appid", appid);
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String create_nonce_str() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static String getJsapiTicket(String accessToken) {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
        StringBuffer sb = new StringBuffer();
        sb.append("access_token=" + accessToken + "&type=jsapi");
        String jsapiTicket = "";
        try {
            jsapiTicket = sendGet(url, sb.toString(), false, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("jsapiTicket:" + jsapiTicket);
        JSONObject js = JSONObject.parseObject(jsapiTicket);
        return js.getString("ticket");
    }

    public static String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        StringBuffer sb = new StringBuffer();
        sb.append("&appid=" + appid + "&secret=" + secret + "&grant_type=client_credential");
        String accessToken = "";
        try {
            accessToken = sendGet(url, sb.toString(), false, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("accessToken:" + accessToken);
        JSONObject js = JSONObject.parseObject(accessToken);
        return js.getString("access_token");
    }

    /**
     * 发送 HTTP GET 请求
     * @param url
     *          请求的 URL 地址
     * @param param
     *          请求的参数内容
     * @param isForm
     *          是否是form请求
     * @param encode
     *          编码方式，null为默认编码
     * @return
     * @throws IOException 
     */
    public static String sendGet(String url, String param, boolean isForm, String encode)
                                                                                         throws IOException {
        String result = "";

        // 打开连接，并设置参数
        URL httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setDoInput(true); // 设置可输入
        conn.setDoOutput(true);// 设置可输出
        conn.setRequestMethod("GET");//设置请求方式为GET
        if (isForm)
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.connect();

        // 输出
        write(conn.getOutputStream(), param, encode);
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        //输入
        result = read(conn.getInputStream(), 1024, ENCODE);
        conn.getInputStream().close();

        // 关闭连接
        conn.disconnect();

        return result;
    }

    /**
     * 发送 HTTP POST 请求
     * @param url
     *          请求的 URL 地址
     * @param param
     *          请求的参数内容
     * @param isForm
     *          是否是form请求
     * @param encode
     *          编码方式，null为默认编码
     * @return
     * @throws IOException 
     */
    public static String sendPost(String url, String param, boolean isForm, String encode)
                                                                                          throws IOException {
        String result = "";

        // 打开连接，并设置参数
        URL httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setDoInput(true); // 设置可输入
        conn.setDoOutput(true);// 设置可输出
        conn.setRequestMethod("POST");//设置请求方式为POST
        if (isForm)
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.connect();

        // 输出
        write(conn.getOutputStream(), param, encode);
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        //输入
        result = read(conn.getInputStream(), 1024, ENCODE);
        conn.getInputStream().close();

        // 关闭连接
        conn.disconnect();

        return result;
    }

    /**
     * 指定输入流和编码方式输入指定信息
     * @param out
     * @param msg
     * @param encode
     * @throws IOException 
     */
    public static void write(OutputStream out, String msg, String encode) throws IOException {
        byte[] b = msg.getBytes(encode);
        out.write(b);
        out.flush();
    }

    /**
     * 指定缓存大小，编码方式读取输入流
     * @param in
     *          输入流
     * @param length
     *          缓存大小
     * @param encode
     *          编码方式
     * @return
     * @throws IOException
     */
    public static String read(InputStream in, int length, String encode) throws IOException {
        StringBuilder result = new StringBuilder();

        byte[] b = new byte[length];
        int len = -1;
        while (-1 != (len = in.read(b)))
            result.append(new String(b, 0, len, encode));

        return result.toString();
    }

    /**
     * 验证签名
     * 
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { token, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * 
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     * 
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
                'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    
    /**
	 * 元转换成分
	 * @param money
	 * @return
	 */
	public static String getMoney(String amount) {
		if(amount==null){
			return "";
		}
		// 金额转化为分为单位
		String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString(); 
	}

}
