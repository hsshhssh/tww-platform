package com.xqh.tww.entity.vo;

import lombok.Data;

/**
 * Created by hssh on 2017/12/19.
 */
@Data
public class TwwDollVO
{
    private Long id;
    private String name;
    private Long amount;
    private String image;
    private Integer status;
    private Long createTime;
    private Long updateTime;
    private String detail;

}
