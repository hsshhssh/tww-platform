package com.xqh.tww.service;

import com.alibaba.fastjson.JSONObject;
import com.xqh.tww.tkmybatis.entity.TwwUser;
import com.xqh.tww.tkmybatis.mapper.TwwUserMapper;
import com.xqh.tww.utils.common.DozerUtils;
import com.xqh.tww.utils.common.ExampleBuilder;
import com.xqh.tww.utils.common.Search;
import org.apache.commons.beanutils.BeanUtils;
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

    public TwwUser insert(TwwUser twwUser)
    {
        long nowTime = System.currentTimeMillis();
        twwUser.setCreateTime(nowTime);
        twwUser.setUpdateTime(nowTime);

        TwwUser oldTwwUser = selectByOpenIdInner(twwUser.getOpenId());
        if(null != oldTwwUser)
        {
            return update(oldTwwUser);
        }

        logger.info("新用户 twwUser:{}", JSONObject.toJSON(twwUser));
        try
        {
            twwUserMapper.insertSelective(twwUser);
        } catch (DuplicateKeyException e)
        {
            return update(twwUser);
        }

        return twwUser;
    }

    private TwwUser update(TwwUser twwUser)
    {
        logger.info("openId:{} 已注册 更新信息：{}", twwUser.getOpenId(), twwUser);
        TwwUser oldTwwUser = selectByOpenIdInner(twwUser.getOpenId());
        TwwUser newTwwUser = DozerUtils.map(twwUser, TwwUser.class);
        newTwwUser.setId(oldTwwUser.getId());
        newTwwUser.setCreateTime(null);
        twwUserMapper.updateByPrimaryKeySelective(newTwwUser);
        return newTwwUser;
    }

    private TwwUser selectByOpenIdInner(String openId)
    {
        Search search = new Search();
        search.put("openId_eq", openId);
        Example example = new ExampleBuilder(TwwUser.class).search(search).build();
        List<TwwUser> users = twwUserMapper.selectByExample(example);
        return users.size() > 0 ? users.get(0) : null;
    }

}
