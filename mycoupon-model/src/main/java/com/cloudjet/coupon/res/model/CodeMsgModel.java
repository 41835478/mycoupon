package com.cloudjet.coupon.res.model;

import java.io.Serializable;
import java.util.Date;

public class CodeMsgModel implements Serializable{

	/**
	 * 短信模板的详情
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 短信中的优惠券名字
	 */
	private String cpName;
	
	/**
	 * 短信中的券码
	 */
	private String cpCode;
	
	/**
	 * 短信中优惠券的过期时间
	 */
	private Date dueTime;
	
	/**
	 * 短信里面URL
	 */
	private String msgUrl;
	
	/**
	 * 短信模板
	 */
	private String msgTemplate;
	
	/**
	 * 券码名称，如 "券码："
	 */
	private String param;
	
	/**
	 * 消息标示 默认jph,holiday(复星旅游)
	 */
	private String msgTag;
	
	public String getMsgTag() {
		return msgTag;
	}
	public void setMsgTag(String msgTag) {
		this.msgTag = msgTag;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getMsgUrl() {
		return msgUrl;
	}
	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl;
	}
	public String getMsgTemplate() {
		return msgTemplate;
	}
	public void setMsgTemplate(String msgTemplate) {
		this.msgTemplate = msgTemplate;
	}
	public String getCpCode() {
		return cpCode;
	}
	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public Date getDueTime() {
		return dueTime;
	}
	public void setDueTime(Date dueTime) {
		this.dueTime = dueTime;
	}
	
	@Override
	public String toString() {
		return "CodeMsgModel [cpName=" + cpName + ", cpCode=" + cpCode + ", dueTime=" + dueTime + ", msgUrl=" + msgUrl
				+ ", msgTemplate=" + msgTemplate + ", param=" + param + ", msgTag=" + msgTag + "]";
	}
	

	
}
