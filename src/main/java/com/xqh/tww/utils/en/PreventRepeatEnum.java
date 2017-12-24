package com.xqh.tww.utils.en;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by hssh on 2017/12/24.
 */
@Getter
@AllArgsConstructor
public enum PreventRepeatEnum
{
    ORDER(1, "订单防重");

    private int code;
    private String name;

}
