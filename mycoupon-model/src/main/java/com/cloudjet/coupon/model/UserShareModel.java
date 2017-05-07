package com.cloudjet.coupon.model;

import java.io.Serializable;

public class UserShareModel implements Serializable{

	/**
	 * 分享券model
	 */
	private static final long serialVersionUID = 1L;
	
	private String shareCode;  //分享用户手机号-券(包)id-0(券)1(包)-分享时间戳
	private String userTel;
	
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getShareCode() {
		return shareCode;
	}
	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}
	@Override
	public String toString() {
		return "UserShareModel [shareCode=" + shareCode + ", userTel=" + userTel + "]";
	}

	
}
