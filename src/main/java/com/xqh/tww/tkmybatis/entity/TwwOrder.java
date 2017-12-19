package com.xqh.tww.tkmybatis.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tww_order")
public class TwwOrder {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 娃娃id
     */
    @Column(name = "doll_id")
    private Long dollId;

    /**
     * 娃娃名称
     */
    @Column(name = "doll_name")
    private String dollName;

    /**
     * 娃娃金额
     */
    @Column(name = "doll_amount")
    private Long dollAmount;

    /**
     * 娃娃图片
     */
    @Column(name = "doll_image")
    private String dollImage;

    /**
     * 当前募集金额
     */
    @Column(name = "current_amount")
    private Long currentAmount;

    /**
     * 状态1募集中 2募集成功
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
}