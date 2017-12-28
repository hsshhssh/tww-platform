package com.xqh.tww.utils.wx;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;

import static com.xqh.tww.utils.wx.pay.util.WeChatUtil.create_nonce_str;

/**
 * Created by hssh on 2017/12/28.
 */
public class WxShare
{
    public static HashMap<String, String> jsSDK_Sign(String url, String ticket, String appId) throws Exception {
        String nonce_str = create_nonce_str();
        String timestamp= String.valueOf(System.currentTimeMillis()/1000);
        String jsapi_ticket= ticket;
        // 注意这里参数名必须全部小写，且必须有序
        String  string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
                + "&timestamp=" + timestamp  + "&url=" + url;
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(string1.getBytes("UTF-8"));
        String signature = byteToHex(crypt.digest());
        HashMap<String, String> jssdk=new HashMap<String, String>();
        jssdk.put("appId", appId);
        jssdk.put("timestamp", timestamp);
        jssdk.put("nonceStr", nonce_str);
        jssdk.put("signature", signature);
        return jssdk;

    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
