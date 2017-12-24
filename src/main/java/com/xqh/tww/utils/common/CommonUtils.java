package com.xqh.tww.utils.common;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by hssh on 2017/5/1.
 */
public class CommonUtils
{

    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);


    /**
     * 返回
     */
    public static void writeResponse(HttpServletResponse resp, Object object) {
        try {
            resp.getWriter().print(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得请求参数
     * @param request
     * @return
     */
    public static TreeMap<String, String> getParams(HttpServletRequest request){
        TreeMap<String, String> map = new TreeMap<String, String>();
        Map reqMap = request.getParameterMap();
        for(Object key:reqMap.keySet()){
            String value = ((String[])reqMap.get(key))[0];
            //System.out.println(key+";"+value);
            map.put(key.toString(),value);
        }
        return map;
    }

    /**
     * 拼接url
     */
    public static String getFullUrl(String host, Map<String, String> params)
    {
        List<String> paramList = Lists.newArrayList();
        for (String s : params.keySet())
        {
            if(StringUtils.isNotBlank(params.get(s)))
            {
                paramList.add(s.trim() + "=" + params.get(s).trim());
            }
        }

        return host + "?" + Joiner.on("&").join(paramList);
    }

    /**
     * 取得零点时间
     */
    public static int getZeroHourTime(int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        return (int) (date.getTime()/1000);
    }

    /**
     * 返回错误信息
     */
    public static void sendError(HttpServletResponse resp, ErrorResponseEunm errorResponseEunm)
    {
        try
        {
            resp.sendError(errorResponseEunm.status, errorResponseEunm.msg);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 参数校验失败 指定错误信息
     */
    public static void sendArgeValidErrorMessage(HttpServletResponse resp, String message)
    {
        try
        {
            resp.sendError(ErrorResponseEunm.INVALID_METHOD_ARGS.status, message);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * md5加密
     * @param plainText
     * @return
     */
    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String generateRandom(int num) {
        String chars = "0123456789";
        char[] rands = new char[num];
        for (int i = 0; i < num; i++) {
            int rand = (int) (Math.random() * 10);
            rands[i] = chars.charAt(rand);
        }
        return String.valueOf(rands);
    }

    public static String getIp(HttpServletRequest req)
    {
        String ip = req.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            logger.info("X-Real-IP :{}", ip);
            return ip;
        }
        ip = req.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            logger.info("X-Forwarded-For :{}", ip);
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            logger.info("remoteAddr: {}", req.getRemoteAddr());
            return req.getRemoteAddr();
        }
    }

}
