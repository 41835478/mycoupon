package com.cloudjet.coupon.res.model;

import java.io.Serializable;

public class ShareCodeResModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否是分享券 0,不是；1，是
	 * */
	private Integer isShare;
	private String encrypShareCode;
	public Integer getIsShare() {
		return isShare;
	}
	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}
	public String getEncrypShareCode() {
		return encrypShareCode;
	}
	public void setEncrypShareCode(String encrypShareCode) {
		this.encrypShareCode = encrypShareCode;
	}
	@Override
	public String toString() {
		return "ShareCodeResModel [isShare=" + isShare + ", encrypShareCode=" + encrypShareCode + "]";
	}
	
	
}
