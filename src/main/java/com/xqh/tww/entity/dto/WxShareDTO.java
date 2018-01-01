package com.xqh.tww.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by hssh on 2017/12/25.
 */
@Data
public class WxShareDTO
{
    @NotBlank
    private String shareUrl;

}
