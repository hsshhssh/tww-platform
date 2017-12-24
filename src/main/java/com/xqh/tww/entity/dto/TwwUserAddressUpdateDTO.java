package com.xqh.tww.entity.dto;

import com.xqh.tww.utils.Constant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TwwUserAddressUpdateDTO extends TwwUserAddressInsertDTO
{

    @NotNull(message = Constant.ERR_MSG)
    @Min(value = 1, message = Constant.ERR_MSG)
    private Long Id;

}
