package com.xqh.tww.entity.vo;

import lombok.Data;

/**
 * Created by hssh on 2017/12/19.
 */
@Data
public class TwwUserAddressVO
{
    private Long id;
    private Long userId;
    private String consigneeName;
    private String consigneePhone;
    private Integer provinceId;
    private String provinceName;
    private Integer cityId;
    private String cityName;
    private Integer countyId;
    private String countyName;
    private String detailAddress;
    private Long createTime;
    private Long updateTime;
}
