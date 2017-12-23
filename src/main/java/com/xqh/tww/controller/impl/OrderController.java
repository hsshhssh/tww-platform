package com.xqh.tww.controller.impl;

import com.github.pagehelper.Page;
import com.xqh.tww.controller.api.IOrderController;
import com.xqh.tww.entity.dto.TwwOrderInsertDTO;
import com.xqh.tww.entity.vo.TwwOrderVO;
import com.xqh.tww.tkmybatis.entity.TwwOrder;
import com.xqh.tww.tkmybatis.mapper.TwwOrderMapper;
import com.xqh.tww.utils.common.DozerUtils;
import com.xqh.tww.utils.common.ExampleBuilder;
import com.xqh.tww.utils.common.PageResult;
import com.xqh.tww.utils.common.Search;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * Created by hssh on 2017/12/19.
 */
@RestController
public class OrderController implements IOrderController
{
    @Resource
    private TwwOrderMapper orderMapper;

    @Override
    public PageResult<TwwOrderVO> list(@RequestParam("search") @Valid @NotNull Search search,
                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size)
    {
        Example example = new ExampleBuilder(TwwOrder.class).search(search).sort(Arrays.asList("id_desc")).build();
        Page<TwwOrder> twwDollPage = (Page<TwwOrder>) orderMapper.selectByExampleAndRowBounds(example, new RowBounds(page, size));
        return new PageResult<>(twwDollPage.getTotal(), DozerUtils.mapList(twwDollPage.getResult(), TwwOrderVO.class));
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
