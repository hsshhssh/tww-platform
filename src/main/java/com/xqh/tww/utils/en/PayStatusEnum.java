package com.xqh.tww.utils.en;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by hssh on 2017/12/24.
 */
@Getter
@AllArgsConstructor
public enum PayStatusEnum
{
    INIT(1, "创建"),
    SUCCESS(2, "支付成功"),
    ;

    private int code;
    private String name;
}
