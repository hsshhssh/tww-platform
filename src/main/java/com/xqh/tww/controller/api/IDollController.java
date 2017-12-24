package com.xqh.tww.controller.api;

import com.xqh.tww.entity.dto.CanPleaseDollDTO;
import com.xqh.tww.entity.dto.ListDTO;
import com.xqh.tww.entity.vo.CanPleaseDollVO;
import com.xqh.tww.entity.vo.TwwDollVO;
import com.xqh.tww.utils.common.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
    @ApiImplicitParams({
        @ApiImplicitParam(name = "search", value = "高级查询对象", required = true, dataType = "Map"),
        @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", dataType = "Integer"),
        @ApiImplicitParam(name = "size", value = "每页条数", defaultValue = "10", dataType = "Integer")
    })
    @PostMapping("list")
    public PageResult<TwwDollVO> list(@RequestBody @Valid @NotNull ListDTO dto);


    @ApiOperation("娃娃详情接口")
    @ApiImplicitParam(name = "id", value = "娃娃id", required = true, dataType = "Long")
    @GetMapping
    public TwwDollVO get(@RequestParam("id") long id);


    @ApiOperation("判断是否可以讨娃娃接口")
    @ApiImplicitParam(name = "dto", value = "判断是否可以讨娃娃实体类", required = true, dataType = "CanPleaseDollDTO")
    @PostMapping("/can/please")
    public CanPleaseDollVO canPlease(@RequestBody @Valid @NotNull CanPleaseDollDTO dto);

}
