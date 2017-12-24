package com.xqh.tww.utils;

import com.xqh.tww.utils.common.CommonUtils;

/**
 * Created by hssh on 2017/12/24.
 */
public class NoUtils
{
    public static String getOrderNo(long uid)
    {
        return "030" + System.currentTimeMillis() + String.format("%10d", uid) + CommonUtils.generateRandom(6);
    }

    public static String getPayNo(long uid)
    {
        return "031" + System.currentTimeMillis() + String.format("%10d", uid) + CommonUtils.generateRandom(6);
    }

}
