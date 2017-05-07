package com.cloudjet.coupon.model;

import java.io.Serializable;

public class JphUserResponse implements Serializable{

	/**
	 *  JPH用户等级信息返回
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 状态码，200 成功  500 错误
	 */
	private String code;
	
	/**
	 * 错误信息
	 */
	private String errorMsg;
	
	/**
	 * 会员ID
	 */
	private String userId;
	
	/**
	 * 会员名称
	 */
	private String userName;
	
	/**
	 * 手机号
	 */
	private String mobileNo;
	
	/**
	 * 会员等级
	 */
	private String userLevel;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	@Override
	public String toString() {
		return "JphUserResponse [code=" + code + ", errorMsg=" + errorMsg + ", userId=" + userId + ", userName="
				+ userName + ", mobileNo=" + mobileNo + ", userLevel=" + userLevel + "]";
	}
	
}
