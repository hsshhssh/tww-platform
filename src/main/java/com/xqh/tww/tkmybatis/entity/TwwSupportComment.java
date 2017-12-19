package com.xqh.tww.tkmybatis.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tww_support_comment")
public class TwwSupportComment {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 赞助id
     */
    @Column(name = "support_id")
    private Long supportId;

    /**
     * 评论用户id
     */
    @Column(name = "from_user_id")
    private Long fromUserId;

    /**
     * 评论用户名
     */
    @Column(name = "from_user_name")
    private String fromUserName;

    /**
     * 被评论人id
     */
    @Column(name = "to_user_id")
    private Long toUserId;

    /**
     * 被评论人名
     */
    @Column(name = "to_user_name")
    private String toUserName;

    /**
     * 评论内容
     */
    private String content;

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
     * 赞助金额
     */
    @Column(name = "pay_amount")
    private Long payAmount;

    /**
     * 赞助时间
     */
    @Column(name = "pay_time")
    private Long payTime;

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