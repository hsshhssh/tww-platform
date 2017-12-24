package com.xqh.tww.tkmybatis.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tww_prevent_repeat")
public class TwwPreventRepeat
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer type;

    @Column(name = "repeat_flag")
    private String repeatFlag;
}