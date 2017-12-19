package com.xqh.tww.utils.common;

/**
 * Created by hssh on 2017/6/4.
 */
public enum ErrorResponseEunm
{
    INVALID_METHOD_ARGS(40003, "参数校验失败"),
    ;



    ErrorResponseEunm(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int status;
    public String msg;
}
