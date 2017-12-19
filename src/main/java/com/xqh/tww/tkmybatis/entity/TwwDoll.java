package com.xqh.tww.tkmybatis.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tww_doll")
public class TwwDoll {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 娃娃名称
     */
    private String name;

    /**
     * 娃娃金额
     */
    private Long amount;

    /**
     * 娃娃主图
     */
    private String image;

    /**
     * 状态1禁用 2启用
     */
    private Integer status;

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

    /**
     * 详情
     */
    private String detail;
}