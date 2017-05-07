package com.cloudjet.coupon.entity.dto;

import com.cloudjet.coupon.entity.BaseEntity;

public class SendRecordDeatilEntity extends BaseEntity{

	/**
	 * 查看发送详情
	 */
	private static final long serialVersionUID = 1L;

	private String infoId;//优惠券id
	private Integer sendType;//发放类型，发放0，补发1
	private Integer userSouceType;//用户范围，等级用户0，导入用户1 活动用户2
	private Integer count;//发布用户数
	private String operatorId;//操作记录Id
	private String operatorName;//操作人姓名
	private String operatorTel;//操作人电话

	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public Integer getSendType() {
		return sendType;
	}
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
	public Integer getUserSouceType() {
		return userSouceType;
	}
	public void setUserSouceType(Integer userSouceType) {
		this.userSouceType = userSouceType;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
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
	
	
}
