package com.xqh.tww.entity.vo;

import lombok.Data;

/**
 * Created by hssh on 2017/12/24.
 */
@Data
public class PayOrderVO
{
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String prepay_id;
    private String signType;
    private String paySign;

}
