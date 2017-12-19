package com.xqh.tww.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/19.
 */
@Data
public class RegisterUserDTO
{
    @NotBlank(message = "微信openId不能为空")
    private String openId;

    @NotBlank(message = "名字不能为空")
    private String name;

    @NotBlank(message = "头像不能为空")
    private String avatar;

    @NotNull
    @Min(1) @Max(2)
    private Integer sex;
}
