package com.xqh.tww.entity.dto;

import com.xqh.tww.utils.Constant;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/20.
 */
@Data
public class TwwOrderInsertDTO
{

    @NotNull(message = "请选择娃娃")
    @Min(value = 1, message = "请选择娃娃")
    private String dollId;

    @NotNull(message = Constant.LOGIN_TIMEOUT)
    @Min(value = 1, message = Constant.LOGIN_TIMEOUT)
    private Long userId;


}
