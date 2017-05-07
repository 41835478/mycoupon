package com.cloudjet.coupon.request;

import java.io.Serializable;

public class SendCouponPullModel implements Serializable{

	/**
	 * 满足规则，推送劵类,消息订阅
	 */
	private static final long serialVersionUID = 1L;
	
	private String userTel;
	private String cpId;
	private String type;//0，分享为券包id;1，券id
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "SendCouponPullModel [userTel=" + userTel + ", cpId=" + cpId + ", type=" + type + "]";
	}
	
	
}
