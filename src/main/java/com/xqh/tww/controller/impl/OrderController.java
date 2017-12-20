package com.xqh.tww.controller.impl;

import com.xqh.tww.controller.api.IOrderController;
import com.xqh.tww.entity.dto.TwwOrderInsertDTO;
import com.xqh.tww.entity.vo.TwwOrderVO;
import com.xqh.tww.utils.common.PageResult;
import com.xqh.tww.utils.common.Search;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by hssh on 2017/12/19.
 */
@RestController
public class OrderController implements IOrderController
{
    @Override
    public PageResult<TwwOrderVO> list(@RequestParam("search") @Valid @NotNull Search search, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size)
    {
        return null;
    }

    @Override
    public long insertOrder(@RequestBody @Valid @NotNull TwwOrderInsertDTO dto)
    {
        return 0;
    }

    @Override
    public TwwOrderVO get(@RequestParam("id") long id)
    {
        return null;
    }
}
