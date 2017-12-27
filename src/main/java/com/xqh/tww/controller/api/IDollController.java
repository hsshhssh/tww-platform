package com.xqh.tww.controller.api;

import com.xqh.tww.entity.dto.CanPleaseDollDTO;
import com.xqh.tww.entity.dto.ListDTO;
import com.xqh.tww.entity.vo.CanPleaseDollVO;
import com.xqh.tww.entity.vo.TwwDollVO;
import com.xqh.tww.utils.common.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    @ApiImplicitParam(name = "dto", value = "查询请求对象", required = true, dataType = "ListDTO")
    @PostMapping("list")
    public PageResult<TwwDollVO> list(@RequestBody @Valid @NotNull ListDTO dto);


    @ApiOperation("娃娃详情接口")
    @ApiImplicitParam(name = "id", value = "娃娃id", required = true, dataType = "Long")
    @GetMapping
    @ApiIgnore
    public TwwDollVO get(@RequestParam("id") long id);


    @ApiOperation("判断是否可以讨娃娃接口")
    @ApiImplicitParam(name = "dto", value = "判断是否可以讨娃娃实体类", required = true, dataType = "CanPleaseDollDTO")
    @PostMapping("/can/please")
    @ApiIgnore
    public CanPleaseDollVO canPlease(@RequestBody @Valid @NotNull CanPleaseDollDTO dto);

}
