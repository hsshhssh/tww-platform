package com.xqh.tww.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/19.
 */
@Data
public class RegisterUserDTO
{
    @NotBlank(message = "微信openId不能为空")
    @Length(max = 50, message = "微信openId不合法")
    private String openId;

    @NotBlank(message = "名字不能为空")
    @Length(max = 50, message = "名字不合法")
    private String name;

    @NotBlank(message = "头像不能为空")
    @Length(max = 200, message = "头像不合法")
    private String avatar;

    @NotNull(message = "性别不能为空")
    @Range(min = 1, max = 2, message = "性别不合法")
    private Integer sex;
}
