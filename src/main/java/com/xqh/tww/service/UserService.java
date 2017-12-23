package com.xqh.tww.service;

import com.xqh.tww.tkmybatis.entity.TwwUser;
import com.xqh.tww.tkmybatis.mapper.TwwUserMapper;
import com.xqh.tww.utils.common.ExampleBuilder;
import com.xqh.tww.utils.common.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hssh on 2017/12/20.
 */
@Service
public class UserService
{
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private TwwUserMapper twwUserMapper;

    public long insert(TwwUser twwUser)
    {
        long nowTime = System.currentTimeMillis();
        twwUser.setCreateTime(nowTime);
        twwUser.setUpdateTime(nowTime);

        try
        {
            twwUserMapper.insertSelective(twwUser);
        } catch (DuplicateKeyException e)
        {
            logger.info("openId:{} 已注册", twwUser.getOpenId());
            return selectByOpenIdInner(twwUser.getOpenId()).getId();
        }

        return twwUser.getId();
    }

    private TwwUser selectByOpenIdInner(String openId)
    {
        Search search = new Search();
        search.put("openId_eq", openId);
        Example example = new ExampleBuilder(TwwUser.class).search(search).build();
        List<TwwUser> users = twwUserMapper.selectByExample(example);
        return users.get(0);
    }

}
