package com.xqh.tww.utils.common;

/**
 * Created by hssh on 2017/6/4.
 */
public enum ErrorResponseEunm
{
    ERROR_GET_OPENID(40001, "获取openId失败"),
    REPEAT_ORDER(40002, "改娃娃已经募集中"),
    INVALID_METHOD_ARGS(40003, "参数校验失败"),
    ERROR_CREATE_ORDER(40004, "创建订单异常"),
    ERROR_CREATE_PAY_ORDER(40005, "创建支付订单失败"),
    ;



    ErrorResponseEunm(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int status;
    public String msg;
}
