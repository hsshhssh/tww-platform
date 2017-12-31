package com.xqh.tww.controller.api;

import com.xqh.tww.entity.dto.RegisterUserDTO;
import com.xqh.tww.entity.dto.TwwUserAddressInsertDTO;
import com.xqh.tww.entity.dto.TwwUserAddressUpdateDTO;
import com.xqh.tww.entity.vo.TwwUserAddressVO;
import com.xqh.tww.entity.vo.TwwUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Created by hssh on 2017/12/19.
 */
@Api("用户相关接口")
@RequestMapping("/xqh/wawa/tww/user")
public interface IUserController
{
    @ApiOperation("用户注册接口")
    @ApiImplicitParam(name = "dto", value = "用户注册实体类", required = true, dataType = "RegisterUserDTO")
    @PutMapping("register")
    @ApiIgnore
    public TwwUserVO register(@RequestBody @Valid RegisterUserDTO dto);

    @ApiOperation("获取收货地址")
    @ApiImplicitParam(name = "uid", value = "用户id", required = true, dataType = "Long")
    @GetMapping("address")
    public TwwUserAddressVO getAddress(@RequestParam(name = "uid") @Min(1) @Valid long uid);

    @ApiOperation("新增收货地址")
    @ApiImplicitParam(name = "dto", value = "新增地址实体类", required = true, dataType = "TwwUserAddressInsertDTO")
    @PutMapping("address")
    public long insertAddress(@RequestBody @Valid TwwUserAddressInsertDTO dto);

    @ApiOperation("修改收货地址")
    @ApiImplicitParam(name = "dto", value = "修改地址实体类", required = true, dataType = "TwwUserAddressUpdateDTO")
    @PostMapping("address")
    public long updateAddress(@RequestBody @Valid TwwUserAddressUpdateDTO dto);
}
