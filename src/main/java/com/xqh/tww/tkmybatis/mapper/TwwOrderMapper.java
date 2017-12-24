package com.xqh.tww.tkmybatis.mapper;

import com.xqh.tww.tkmybatis.entity.TwwOrder;
import tk.mybatis.mapper.common.Mapper;

public interface TwwOrderMapper extends Mapper<TwwOrder>
{
    public TwwOrder selectByPrimaryKeyLock(Long id);
}