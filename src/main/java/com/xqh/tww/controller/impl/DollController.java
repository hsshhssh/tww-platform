package com.xqh.tww.controller.impl;

import com.github.pagehelper.Page;
import com.xqh.tww.controller.api.IDollController;
import com.xqh.tww.entity.dto.CanPleaseDollDTO;
import com.xqh.tww.entity.dto.ListDTO;
import com.xqh.tww.entity.vo.CanPleaseDollVO;
import com.xqh.tww.entity.vo.TwwDollVO;
import com.xqh.tww.tkmybatis.entity.TwwDoll;
import com.xqh.tww.tkmybatis.mapper.TwwDollMapper;
import com.xqh.tww.utils.common.DozerUtils;
import com.xqh.tww.utils.common.ExampleBuilder;
import com.xqh.tww.utils.common.PageResult;
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
public class DollController implements IDollController
{

    @Resource
    private TwwDollMapper dollMapper;

    @Override
    public PageResult<TwwDollVO> list(@RequestBody @Valid @NotNull ListDTO dto)
    {
        Example example = new ExampleBuilder(TwwDoll.class).search(dto.getSearch()).sort(Arrays.asList("id_desc")).build();
        Page<TwwDoll> twwDollPage = (Page<TwwDoll>) dollMapper.selectByExampleAndRowBounds(example, new RowBounds(dto.getPage(), dto.getSize()));
        return new PageResult<>(twwDollPage.getTotal(), DozerUtils.mapList(twwDollPage.getResult(), TwwDollVO.class));
    }

    @Override
    public TwwDollVO get(@RequestParam("id") long id)
    {
        return null;
    }

    @Override
    public CanPleaseDollVO canPlease(@RequestBody @Valid @NotNull CanPleaseDollDTO dto)
    {
        return null;
    }
}
