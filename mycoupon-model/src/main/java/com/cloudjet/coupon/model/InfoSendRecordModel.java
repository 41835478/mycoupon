package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.Date;

public class InfoSendRecordModel implements Serializable{

	/**
	 * 发放记录 model
	 */
	private static final long serialVersionUID = 1L;
	
	public enum SendType{
		/**
		 * 发放
		 * */
		send,
		/**
		 * 补发
		 * */
		reissue
	}
	
	
	private Integer userScopeType;//等级用户0,导入用户1,活动用户2,券码用户3
	
	private Integer sendType;//发放 0 ,补发 1,消息
	
	private String operatorName;//操作人姓名
	
	private String operatorTel;//操作人电话
	
	/** 
	 * 发布时间
	 * */
	private Date sendTime;
	
	/**
	 * 发放用户数
	 * */
	private Integer count;
	
	
	/**
	 * 优惠券id
	 * */
	private String cpId;
	
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getUserScopeType() {
		return userScopeType;
	}
	public void setUserScopeType(Integer userScopeType) {
		this.userScopeType = userScopeType;
	}
	public Integer getSendType() {
		return sendType;
	}
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatorTel() {
		return operatorTel;
	}
	public void setOperatorTel(String operatorTel) {
		this.operatorTel = operatorTel;
	}
	@Override
	public String toString() {
		return "InfoSendRecordModel [userScopeType=" + userScopeType + ", sendType=" + sendType + ", operatorName="
				+ operatorName + ", operatorTel=" + operatorTel + ", sendTime=" + sendTime + ", count=" + count
				+ ", cpId=" + cpId + "]";
	}

	
}
