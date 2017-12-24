package com.xqh.tww.controller.impl;

import com.xqh.tww.controller.api.IUserController;
import com.xqh.tww.entity.dto.RegisterUserDTO;
import com.xqh.tww.entity.dto.TwwUserAddressInsertDTO;
import com.xqh.tww.entity.dto.TwwUserAddressUpdateDTO;
import com.xqh.tww.entity.vo.TwwUserAddressVO;
import com.xqh.tww.entity.vo.TwwUserVO;
import com.xqh.tww.service.AddressService;
import com.xqh.tww.service.UserService;
import com.xqh.tww.tkmybatis.entity.TwwUser;
import com.xqh.tww.tkmybatis.entity.TwwUserAddress;
import com.xqh.tww.tkmybatis.mapper.TwwUserAddressMapper;
import com.xqh.tww.utils.common.DozerUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Created by hssh on 2017/12/19.
 */
@RestController
public class UserController implements IUserController
{
    @Resource
    private UserService userService;
    @Resource
    private AddressService addressService;
    @Resource
    private TwwUserAddressMapper addressMapper;

    @Override
    public TwwUserVO register(@RequestBody @Valid RegisterUserDTO dto)
    {
        TwwUser twwUser = DozerUtils.map(dto, TwwUser.class);

        return DozerUtils.map(userService.insert(twwUser), TwwUserVO.class);
    }

    @Override
    public TwwUserAddressVO getAddress(@RequestParam(name = "uid") @Min(1) @Valid long uid)
    {
        TwwUserAddress twwUserAddress = addressService.selectByUserId(uid);
        if(null == twwUserAddress)
        {
            return null;
        }
        else
        {
            return DozerUtils.map(twwUserAddress, TwwUserAddressVO.class);
        }
    }

    @Override
    public long insertAddress(@RequestBody @Valid TwwUserAddressInsertDTO dto)
    {
        TwwUserAddress userAddress = DozerUtils.map(dto, TwwUserAddress.class);
        long nowTime = System.currentTimeMillis();
        userAddress.setCreateTime(nowTime);
        userAddress.setUpdateTime(nowTime);
        addressMapper.insertSelective(userAddress);

        return userAddress.getId();
    }

    @Override
    public long updateAddress(@RequestBody @Valid TwwUserAddressUpdateDTO dto)
    {
        TwwUserAddress userAddress = DozerUtils.map(dto, TwwUserAddress.class);
        userAddress.setUpdateTime(System.currentTimeMillis());
        addressMapper.updateByPrimaryKeySelective(userAddress);

        return userAddress.getId();
    }
}
