package com.xqh.tww.utils.en;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by hssh on 2017/12/24.
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum
{
    RAISE(1, "募集中"),
    SUCCESS(2, "募集成功"),
    ;

    private int code;
    private String name;


}
