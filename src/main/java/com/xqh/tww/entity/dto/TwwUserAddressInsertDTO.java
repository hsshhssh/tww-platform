package com.xqh.tww.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/19.
 */
@Data
public class TwwUserAddressInsertDTO
{
    @NotNull(message = "用户不合法")
    protected Long userId;

    @NotBlank(message = "请填写收货人名")
    @Length(max = 50, message = "收货人名不合法")
    protected String consigneeName;

    @NotBlank(message = "请填写收货人联系方式")
    @Length(max = 20, message = "收货人联系方式不合法")
    protected String consigneePhone;

    @NotNull(message = "请选择省份")
    @Min(value = 0, message = "省份不合法")
    protected Integer provinceId;

    @NotBlank(message = "请选择省份")
    @Length(max = 10, message = "省份不合法")
    protected String provinceName;

    @NotNull(message = "请选择市")
    @Min(value = 0, message = "市不合法")
    protected Integer cityId;

    @NotBlank(message = "请选择市")
    @Length(max = 10, message = "市不合法")
    protected String cityName;

    @NotNull(message = "请选择区")
    @Min(value = 0, message = "区不合法")
    protected Integer countyId;

    @NotBlank(message = "请选择区")
    @Length(max = 10, message = "请选择区")
    protected String countyName;

    @NotBlank(message = "请填写详细地址")
    @Length(max = 200, message = "详细地址不合法")
    protected String detailAddress;

}
