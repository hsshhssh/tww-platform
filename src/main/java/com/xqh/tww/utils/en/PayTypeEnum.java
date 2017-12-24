package com.xqh.tww.utils.en;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by hssh on 2017/12/24.
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum
{
    WXGZH(5, "微信公众号"),
    ;

    private int code;
    private String name;
}
