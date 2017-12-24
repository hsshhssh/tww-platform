package com.xqh.tww.utils.config;

import lombok.Data;
import org.hssh.common.zkconf.Value;
import org.springframework.stereotype.Component;

/**
 * Created by hssh on 2017/12/24.
 */
@Component
@Data
public class CommonConfig
{
    @Value(path = "/config/zkconf/wawa_tww.conf", key = "host")
    private String host;

    @Value(path = "/config/zkconf/wawa_tww.conf", key = "mchId")
    private String mchId;

    @Value(path = "/config/zkconf/wawa_tww.conf", key = "appId")
    private String appId;

    @Value(path = "/config/zkconf/wawa_tww.conf", key = "key")
    private String key;


}
