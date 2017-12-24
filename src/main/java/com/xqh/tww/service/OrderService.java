package com.xqh.tww.service;

import com.xqh.tww.entity.dto.TwwOrderInsertDTO;
import com.xqh.tww.tkmybatis.entity.TwwDoll;
import com.xqh.tww.tkmybatis.entity.TwwOrder;
import com.xqh.tww.tkmybatis.entity.TwwUser;
import com.xqh.tww.utils.NoUtils;
import com.xqh.tww.utils.en.OrderStatusEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hssh on 2017/12/24.
 */
@Service
public class OrderService
{
    @Resource
    private PreventRepeatService preventRepeatService;


    public String getOrderRepeatFlag(TwwOrderInsertDTO dto)
    {
        return String.format("%10d-%10d", dto.getUserId(), dto.getDollId());
    }

    public TwwOrder buildOrder(TwwDoll doll, TwwUser user)
    {
        long nowTime = System.currentTimeMillis();

        TwwOrder order = new TwwOrder();
        order.setOrderNo(NoUtils.getOrderNo(user.getId()));
        order.setUserId(user.getId());
        order.setDollId(doll.getId());
        order.setDollName(doll.getName());
        order.setDollAmount(doll.getAmount());
        order.setDollImage(doll.getImage());
        order.setCurrentAmount(0L);
        order.setStatus(OrderStatusEnum.RAISE.getCode());
        order.setCreateTime(nowTime);
        order.setUpdateTime(nowTime);

        return order;
    }



}
