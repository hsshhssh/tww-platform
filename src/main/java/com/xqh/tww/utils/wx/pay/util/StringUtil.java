/**
 * funball.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.xqh.tww.utils.wx.pay.util;


/**
 * 有关字符串处理的工具类。
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 * @author admoy
 * @version $Id: StringUtil.java, v 0.1 2015年4月29日 上午10:41:42 admoy Exp $
 */
public class StringUtil {
	
	/**
     * 检查字符串是否不是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * <pre>
     * StringUtil.isBlank(null)      = false
     * StringUtil.isBlank("")        = false
     * StringUtil.isBlank(" ")       = false
     * StringUtil.isBlank("bob")     = true
     * StringUtil.isBlank("  bob  ") = true
     * </pre>
     *
     * @param str 要检查的字符串
     *
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isNotBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }
	
    /**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str 要检查的字符串
     *
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }
    /**
	 * 把key=value追加到加密/签名字符串最后.
	 * 
	 * @param buf
	 * @param key
	 * @param value
	 */
	public static void appendSignPara(StringBuffer buf, String key, String value) {
		if (StringUtil.isNotBlank(value)) {
			buf.append(key).append('=').append(value).append('&');
		}
	}
	
	/**
	 * 把key=value追加到加密/签名字符串的末尾.字符串不再继续增加新的key=value.
	 * 
	 * @param buf
	 * @param key
	 * @param value
	 */
	public static void appendLastSignPara(StringBuffer buf, String key,
			String value) {
		if (StringUtil.isNotBlank(value)) {
			buf.append(key).append('=').append(value);
		}
		if(!"key".equals(key)){
			buf.append("&");
		}
	}
	
}
