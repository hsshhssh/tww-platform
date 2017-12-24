package com.xqh.tww.service;

import com.xqh.tww.tkmybatis.entity.TwwDoll;
import com.xqh.tww.tkmybatis.entity.TwwOrder;
import com.xqh.tww.tkmybatis.entity.TwwUser;
import com.xqh.tww.utils.NoUtils;
import com.xqh.tww.utils.en.OrderStatusEnum;
import org.springframework.stereotype.Service;

/**
 * Created by hssh on 2017/12/24.
 */
@Service
public class OrderService
{

    public String getOrderRepeatFlag(Long userId, Long dollId)
    {
        return String.format("%010d-%010d", userId, dollId);
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
