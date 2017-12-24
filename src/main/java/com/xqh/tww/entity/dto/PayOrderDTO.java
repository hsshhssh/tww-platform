package com.xqh.tww.entity.dto;

import com.xqh.tww.utils.Constant;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/24.
 */
@Data
public class PayOrderDTO
{
    @NotNull(message = Constant.LOGIN_TIMEOUT)
    @Min(value = 1, message = Constant.LOGIN_TIMEOUT)
    private Long userId;

    @NotNull(message = "请选择娃娃")
    @Min(value = 1, message = "请选择娃娃")
    private Long dollId;

    @NotNull(message = "请选择订单")
    @Min(value = 1, message = "请选择订单")
    private Long orderId;

    @NotNull(message = "请输入金额")
    @Min(value = 1, message = "请输入正确的金额")
    private Long amount;

}






