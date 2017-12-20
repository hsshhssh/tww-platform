package com.xqh.tww.controller.api;

import com.xqh.tww.entity.dto.SupportDTO;
import com.xqh.tww.entity.vo.SupportVO;
import com.xqh.tww.entity.vo.TwwSupportVO;
import com.xqh.tww.utils.common.PageResult;
import com.xqh.tww.utils.common.Search;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/19.
 */
@Api("赞助相关接口")
@RequestMapping("/xqh/wawa/tww/support")
public interface ISupportController
{
    @ApiOperation("赞助列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search", value = "高级查询对象", required = true, dataType = "Map"),
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", defaultValue = "10", dataType = "Integer")
    })
    @GetMapping("list")
    public PageResult<TwwSupportVO> list(@RequestParam("search") @Valid @NotNull Search search,
                                         @RequestParam(value = "page", defaultValue = "1")  int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size);

    @ApiOperation("赞助接口")
    @ApiImplicitParam(name = "dto", value = "赞助接口实体类", required = true, dataType = "SupportDTO")
    @PostMapping
    public SupportVO support(@RequestBody @NotNull @Valid SupportDTO dto);


    @ApiOperation("赞助回调接口")
    @GetMapping
    public String supportCallback(HttpServletRequest req, HttpServletResponse resp);
}
