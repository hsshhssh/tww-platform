package com.xqh.tww.entity.dto;

import com.xqh.tww.utils.common.Search;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/24.
 */
@Data
@ApiModel(description = "查询请求对象")
public class ListDTO
{
    @NotNull
    @ApiModelProperty("高级查询对象")
    private Search search;

    @NotNull
    @ApiModelProperty("页码")
    private Integer page;

    @NotNull
    @ApiModelProperty("每页条数")
    private Integer size;
}
