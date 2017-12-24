package com.xqh.tww.entity.vo;

import lombok.Data;

/**
 * Created by hssh on 2017/12/24.
 */
@Data
public class TwwUserVO
{

    private Long id;
    private String openId;
    private String name;
    private String avatar;
    private Integer sex;
    private Long createTime;
    private Long updateTime;

}
