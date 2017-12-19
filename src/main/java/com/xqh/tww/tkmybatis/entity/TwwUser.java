package com.xqh.tww.tkmybatis.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tww_user")
public class TwwUser {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 微信openId
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 性别1男 2女
     */
    private Integer sex;

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