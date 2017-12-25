package com.xqh.tww.controller.api;

import com.xqh.tww.entity.dto.ListDTO;
import com.xqh.tww.entity.dto.PayOrderDTO;
import com.xqh.tww.entity.dto.TwwOrderInsertDTO;
import com.xqh.tww.entity.vo.TwwOrderVO;
import com.xqh.tww.utils.common.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by hssh on 2017/12/19.
 */
@Api("订单相关接口")
@RequestMapping("/xqh/wawa/tww/order")
public interface IOrderController
{
    @ApiOperation("订单列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search", value = "高级查询对象", required = true, dataType = "Map"),
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", defaultValue = "10", dataType = "Integer")
    })
    @PostMapping("list")
    public PageResult<TwwOrderVO> list(@RequestBody @Valid @NotNull ListDTO dto);

    @ApiOperation("创建订单接口")
    @ApiImplicitParam(name = "dto", value = "创建订单实体类", required = true, dataType = "TwwOrderInsertDTO")
    @PutMapping
    public long insertOrder(@RequestBody @Valid @NotNull TwwOrderInsertDTO dto,
                            HttpServletResponse resp);


    @ApiOperation("订单详情接口")
    @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "Long")
    @GetMapping
    @ApiIgnore
    public TwwOrderVO get(@RequestParam("id") long id);


    @ApiOperation("支付订单接口")
    @ApiImplicitParam(name = "dto", value = "订单支付请求实体类", required = true, dataType = "PayOrderDTO")
    @PostMapping("payOrder")
    @ApiIgnore
    public Map<String, Object> payOrder(@RequestBody @Valid @NotNull PayOrderDTO dto,
                                        HttpServletRequest req,
                                        HttpServletResponse resp);


    @ApiOperation("支付订单回调接口")
    @GetMapping("payOrder/callback")
    @ApiIgnore
    public void payOrderCallback(HttpServletRequest req, HttpServletResponse resp);


}
