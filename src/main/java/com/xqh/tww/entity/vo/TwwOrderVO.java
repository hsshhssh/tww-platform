package com.xqh.tww.entity.vo;

import lombok.Data;

/**
 * Created by hssh on 2017/12/20.
 */
@Data
public class TwwOrderVO
{
    private Long id;
    private String orderNo;
    private Long userId;
    private Long dollId;
    private String dollName;
    private Long dollAmount;
    private String dollImage;
    private Long currentAmount;
    private Integer status;
    private Long createTime;
    private Long updateTime;
}
