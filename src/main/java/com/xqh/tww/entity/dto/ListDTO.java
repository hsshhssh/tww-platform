package com.xqh.tww.entity.dto;

import com.xqh.tww.utils.common.Search;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/24.
 */
@Data
public class ListDTO
{
    @NotNull
    private Search search;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;
}
