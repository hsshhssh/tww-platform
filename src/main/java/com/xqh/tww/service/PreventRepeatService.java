package com.xqh.tww.service;

import com.xqh.tww.tkmybatis.entity.TwwPreventRepeat;
import com.xqh.tww.tkmybatis.mapper.TwwPreventRepeatMapper;
import com.xqh.tww.utils.common.ExampleBuilder;
import com.xqh.tww.utils.common.Search;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hssh on 2017/12/24.
 */
@Service
public class PreventRepeatService
{
    private static Logger logger = LoggerFactory.getLogger(PreventRepeatService.class);

    @Resource
    private TwwPreventRepeatMapper preventRepeatMapper;

    public boolean isRepeat(int type, String repeatFlag)
    {

        Search search = new Search();
        search.put("type_eq", type);
        search.put("repeatFlag_eq", repeatFlag);
        List<TwwPreventRepeat> preventRepeatList = preventRepeatMapper.selectByExample(new ExampleBuilder(TwwPreventRepeat.class).search(search).build());

        if(CollectionUtils.isNotEmpty(preventRepeatList))
        {
            logger.info("重复请求 type:{}, repeatFlag:{}", type, repeatFlag);
            return false;
        }

        TwwPreventRepeat preventRepeat = new TwwPreventRepeat();
        preventRepeat.setType(type);
        preventRepeat.setRepeatFlag(repeatFlag);
        try
        {
            preventRepeatMapper.insertSelective(preventRepeat);
        } catch (DuplicateKeyException e)
        {
            logger.info("重复请求 type:{}, repeatFlag:{}", type, repeatFlag);
            return false;
        }

        logger.info("无重复请求 type:{}, repeatFlag:{}", type, repeatFlag);
        return true;
    }

    public boolean removeRepeatFlag(int type, String repeatFlag)
    {
        Search search = new Search();
        search.put("type_eq", type);
        search.put("repeatFlag_eq", repeatFlag);
        Example example = new ExampleBuilder(TwwPreventRepeat.class).search(search).build();
        preventRepeatMapper.deleteByExample(example);

        return true;
    }


}
