package com.cloudjet.coupon.entity.dto;

import java.util.Date;

import com.cloudjet.coupon.entity.BaseEntity;

public class CodeMsgDetailEntity extends BaseEntity{

	/**
	 * 券码发送短信详情
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 优惠券名字
	 */
	private String cpName;
	
	/**
	 * 优惠券开始使用时间
	 */
	private Date beginTime;
	
	/**
	 * 优惠券截止使用时间
	 */
	private Date dueTime;
	
	/**
	 * 短信里面URL
	 */
	private String msgUrl;
	
	/**
	 * 短信里面URL
	 */
	private String msgTemplate;
	
	/**
	 * 短信参数，如 "券码："
	 */
	private String param;
	
	/**
	 * 消息标示 默认jph,holiday(复星旅游)
	 */
	private String msgTag;
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getDueTime() {
		return dueTime;
	}
	public void setDueTime(Date dueTime) {
		this.dueTime = dueTime;
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
	public String getMsgTag() {
		return msgTag;
	}
	public void setMsgTag(String msgTag) {
		this.msgTag = msgTag;
	}
	@Override
	public String toString() {
		return "CodeMsgDetailEntity [cpName=" + cpName + ", beginTime=" + beginTime + ", dueTime=" + dueTime
				+ ", msgUrl=" + msgUrl + ", msgTemplate=" + msgTemplate + ", param=" + param + ", msgTag=" + msgTag
				+ "]";
	}
	
}
