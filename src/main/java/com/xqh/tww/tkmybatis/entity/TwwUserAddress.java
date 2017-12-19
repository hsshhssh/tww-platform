package com.xqh.tww.tkmybatis.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tww_user_address")
public class TwwUserAddress {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 收货人名
     */
    @Column(name = "consignee_name")
    private String consigneeName;

    /**
     * 收货人手机
     */
    @Column(name = "consignee_phone")
    private String consigneePhone;

    /**
     * 省id
     */
    @Column(name = "province_id")
    private Integer provinceId;

    /**
     * 省名称
     */
    @Column(name = "province_name")
    private String provinceName;

    /**
     * 市id
     */
    @Column(name = "city_id")
    private Integer cityId;

    /**
     * 市名称
     */
    @Column(name = "city_name")
    private String cityName;

    /**
     * 区id
     */
    @Column(name = "county_id")
    private Integer countyId;

    /**
     * 区名称
     */
    @Column(name = "county_name")
    private String countyName;

    /**
     * 详细地址
     */
    @Column(name = "detail_address")
    private String detailAddress;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Long createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Long updateTime;
}