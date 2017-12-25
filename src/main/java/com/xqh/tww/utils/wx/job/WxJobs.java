package com.xqh.tww.utils.wx.job;

import com.xqh.tww.utils.wx.auth.WxQuartzBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by hssh on 2017/12/25.
 */
@Component
public class WxJobs
{
    private static Logger logger = LoggerFactory.getLogger(WxJobs.class);

    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void updateTokenAndTicket()
    {
        logger.info("updateTokenAndTicket start.......");
        WxQuartzBean.updateAccessTokenAndTicket();
        logger.info("updateTokenAndTicket end.......");
    }

}
