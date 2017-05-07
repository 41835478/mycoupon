package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;

public class UserSourceModel implements Serializable{

	public enum UserSourceType{
		/**
		 * 等级选择
		 * */
		level,
		/**
		 * 导入
		 * */
		importUser,
		
		/**
		 * 通用
		 * */
		common,
		
		/**
		 * 消息推送（分享满足条件）
		 * */
		msg
		
	}
	
	public enum UserSourceWay{
		/**
		 * 发放用户
		 * */
		provide,
		/**
		 * 用户领取
		 * */
		pull
		
	}
	
	/**
	 * 优惠券 发放用户条件
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 优惠券id
	 * */
	private String cpId;
	/**
	 * 发放用户类型选择 0，等级类型选择；1，导入用户方式；2 通用
	 * */
	private Integer type;
	
	/**
	 * 0，发放用户；1，用户领取（仅仅用作默认用户获取券方式）
	 * */
	private Integer way;
	
	/**
	 * 用户等级 选择列表
	 * */
	private List<String> params;
	
	/**
	 * 操作人
	 * */
	private InfoOperatorModel operator;
	
	/**
	 * 发放记录
	 */
	private InfoSendRecordModel sendRecord;
	
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
	public InfoOperatorModel getOperator() {
		return operator;
	}
	public void setOperator(InfoOperatorModel operator) {
		this.operator = operator;
	}
	public InfoSendRecordModel getSendRecord() {
		return sendRecord;
	}
	public void setSendRecord(InfoSendRecordModel sendRecord) {
		this.sendRecord = sendRecord;
	}
	public Integer getWay() {
		return way;
	}
	public void setWay(Integer way) {
		this.way = way;
	}
	@Override
	public String toString() {
		return "UserSourceModel [cpId=" + cpId + ", type=" + type + ", way=" + way + ", params=" + params
				+ ", operator=" + operator + ", sendRecord=" + sendRecord + "]";
	}
	
	
}
