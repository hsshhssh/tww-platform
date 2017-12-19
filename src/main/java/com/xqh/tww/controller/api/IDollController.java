package com.xqh.tww.controller.api;

import com.xqh.tww.entity.vo.TwwDollVO;
import com.xqh.tww.utils.common.PageResult;
import com.xqh.tww.utils.common.Search;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/19.
 */
@Api("娃娃相关接口")
@RequestMapping("/xqh/wawa/tww/doll")
public interface IDollController
{
    @ApiOperation("娃娃列表接口")
    @GetMapping("list")
    public PageResult<TwwDollVO> list(@RequestParam("search") @Valid @NotNull Search search,
                                      @RequestParam(value = "page", defaultValue = "1")  int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size)

}
