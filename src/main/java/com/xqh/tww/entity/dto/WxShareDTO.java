package com.xqh.tww.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/25.
 */
@Data
public class WxShareDTO
{
    @NotNull
    @Min(1)
    private Long orderId;

    @NotBlank
    private String shareUrl;

}
