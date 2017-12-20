package com.xqh.tww.controller.impl;

import com.xqh.tww.controller.api.ISupportController;
import com.xqh.tww.entity.dto.SupportDTO;
import com.xqh.tww.entity.vo.SupportVO;
import com.xqh.tww.entity.vo.TwwSupportVO;
import com.xqh.tww.utils.common.PageResult;
import com.xqh.tww.utils.common.Search;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/19.
 */
@RestController
public class SupportController implements ISupportController
{
    @Override
    public PageResult<TwwSupportVO> list(@RequestParam("search") @Valid @NotNull Search search, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size)
    {
        return null;
    }

    @Override
    public SupportVO support(@RequestBody @NotNull @Valid SupportDTO dto)
    {
        return null;
    }

    @Override
    public String supportCallback(HttpServletRequest req, HttpServletResponse resp)
    {
        return null;
    }
}
