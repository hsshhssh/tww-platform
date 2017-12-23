package com.xqh.tww.service;

import com.xqh.tww.tkmybatis.entity.TwwUserAddress;
import com.xqh.tww.tkmybatis.mapper.TwwUserAddressMapper;
import com.xqh.tww.utils.common.ExampleBuilder;
import com.xqh.tww.utils.common.Search;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hssh on 2017/12/20.
 */
@Service
public class AddressService
{
    @Resource
    private TwwUserAddressMapper addressMapper;

    public TwwUserAddress selectByUserId(long userId)
    {
        Search search = new Search();
        search.put("userId_eq", userId);
        Example example = new ExampleBuilder(TwwUserAddress.class).search(search).build();
        List<TwwUserAddress> userAddressList = addressMapper.selectByExample(example);
        return userAddressList.get(0);
    }

}
