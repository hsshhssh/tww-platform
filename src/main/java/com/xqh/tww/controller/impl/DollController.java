package com.xqh.tww.controller.impl;

import com.xqh.tww.controller.api.IDollController;
import com.xqh.tww.entity.vo.TwwDollVO;
import com.xqh.tww.utils.common.PageResult;
import com.xqh.tww.utils.common.Search;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/19.
 */
@RestController
public class DollController implements IDollController
{

    @Override
    public PageResult<TwwDollVO> list(@RequestParam("search") @Valid @NotNull Search search, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size)
    {
        return null;
    }
}
