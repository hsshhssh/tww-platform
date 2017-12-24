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

    @Value(path = "/config/zkconf/wawa_tww.conf", key = "payKey")
    private String payKey;

    @Value(path = "/config/zkconf/wawa_tww.conf", key = "payUserId")
    private String payUserId;

    @Value(path = "/config/zkconf/wawa_tww.conf", key = "payAppId")
    private String payAppId;


}
