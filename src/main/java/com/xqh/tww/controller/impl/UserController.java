package com.xqh.tww.controller.impl;

import com.xqh.tww.controller.api.IUserController;
import com.xqh.tww.entity.dto.RegisterUserDTO;
import com.xqh.tww.entity.dto.TwwUserAddressInsertDTO;
import com.xqh.tww.entity.dto.TwwUserAddressUpdateDTO;
import com.xqh.tww.entity.vo.TwwUserAddressVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Created by hssh on 2017/12/19.
 */
@RestController
public class UserController implements IUserController
{

    @Override
    public long register(@RequestBody @Valid RegisterUserDTO dto)
    {
        return 0;
    }

    @Override
    public TwwUserAddressVO getAddress(@RequestParam(name = "uid") @Min(1) @Valid long uid)
    {
        return null;
    }

    @Override
    public long insertAddress(@RequestBody @Valid TwwUserAddressInsertDTO dto)
    {
        return 0;
    }

    @Override
    public long updateAddress(@RequestBody @Valid TwwUserAddressUpdateDTO dto)
    {
        return 0;
    }
}
