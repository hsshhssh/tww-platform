package com.xqh.tww.tkmybatis.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tww_order_support")
public class TwwOrderSupport {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 支付id
     */
    @Column(name = "pay_id")
    private Long payId;

    /**
     * 支付号
     */
    @Column(name = "pay_no")
    private String payNo;

    /**
     * 支付人id
     */
    @Column(name = "pay_user_id")
    private Long payUserId;

    /**
     * 支付人名
     */
    @Column(name = "pay_user_name")
    private String payUserName;

    /**
     * 支付人头像
     */
    @Column(name = "pay_user_avatar")
    private String payUserAvatar;

    /**
     * 支付金额
     */
    @Column(name = "pay_amount")
    private Long payAmount;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 娃娃id
     */
    @Column(name = "doll_id")
    private Long dollId;

    /**
     * 是否点赞 0否 1是
     */
    @Column(name = "id_like")
    private Integer idLike;

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