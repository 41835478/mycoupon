package com.cloudjet.coupon.res.model;

import java.io.Serializable;

public class MsgResponse implements Serializable{

	/**
	 * 短信网关返回model
	 */
	private static final long serialVersionUID = 1L;
	private String smsCode;
	private String resultCode;
	private String resultMessage;
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	@Override
	public String toString() {
		return "MsgResponse [smsCode=" + smsCode + ", resultCode=" + resultCode + ", resultMessage=" + resultMessage
				+ "]";
	}
	
	
}
