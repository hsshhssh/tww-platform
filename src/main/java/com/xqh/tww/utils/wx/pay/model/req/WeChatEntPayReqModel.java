/**
 * Funball.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.xqh.tww.utils.wx.pay.model.req;

/**
 * 
 * @author White Wolf
 * @version $Id: WeChatRefundReqModel.java, v 0.1 2015-12-4 上午10:46:16 White Wolf Exp $
 */
public class WeChatEntPayReqModel {

    private String appId;
    private String mchId;
    private String deviceInfo;
    private String nonoceStr;
    private String sign;
    private String partnerTradeNo;
    private String openid;
    private String checkName;
    private String reUserName;
    private String amount;
    private String desc;
    private String spbillCreateIp;

   

    public String getAppId() {
		return appId;
	}



	public void setAppId(String appId) {
		this.appId = appId;
	}



	public String getMchId() {
		return mchId;
	}



	public void setMchId(String mchId) {
		this.mchId = mchId;
	}



	public String getDeviceInfo() {
		return deviceInfo;
	}



	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}



	public String getNonoceStr() {
		return nonoceStr;
	}



	public void setNonoceStr(String nonoceStr) {
		this.nonoceStr = nonoceStr;
	}



	public String getSign() {
		return sign;
	}



	public void setSign(String sign) {
		this.sign = sign;
	}



	public String getPartnerTradeNo() {
		return partnerTradeNo;
	}



	public void setPartnerTradeNo(String partnerTradeNo) {
		this.partnerTradeNo = partnerTradeNo;
	}



	public String getOpenid() {
		return openid;
	}



	public void setOpenid(String openid) {
		this.openid = openid;
	}



	public String getCheckName() {
		return checkName;
	}



	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}



	public String getReUserName() {
		return reUserName;
	}



	public void setReUserName(String reUserName) {
		this.reUserName = reUserName;
	}



	public String getAmount() {
		return amount;
	}



	public void setAmount(String amount) {
		this.amount = amount;
	}



	public String getDesc() {
		return desc;
	}



	public void setDesc(String desc) {
		this.desc = desc;
	}



	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}



	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}


/*
	*//** 
     * @see Object#toString()
     *//*
    @Override
    public String toString() {
    	"appid=" + appId + "&attach=" + attach + "&body=" + body
        + (!StringUtil.isNotBlank(deviceInfo) ? "" : "&device_info=" + deviceInfo) + "&mch_id="
        + mchId + "&nonce_str=" + nonceStr + "&notify_url=" + notifyUrl
        + (!StringUtil.isNotBlank(openId) ? "" : "&openid=" + openId) + "&out_trade_no="
        + outTradeNo + "&spbill_create_ip=" + spbillCreateIp + "&total_fee=" + totalFee
        + "&trade_type=" + tradeType
        
        return "appid=" + this.appId + "&mch_id=" + this.mchId + "&nonce_str=" + this.nonoceStr
               + "&op_user_id=" + this.opUserId + "&out_refund_no=" + this.outRefundNo
               + "&out_trade_no=" + this.outTradeNo + "&refund_fee=" + this.refundFee
               + "&total_fee=" + this.totalFee;
    }
    private String appId;
    private String mchId;
    private String deviceInfo;
    private String nonoceStr;
    private String sign;
    private String partnerTradeNo;
    private String openid;
    private String checkName;
    private String reUserName;
    private String amount;
    private String desc;
    private String spbillCreateIp;*/
}
