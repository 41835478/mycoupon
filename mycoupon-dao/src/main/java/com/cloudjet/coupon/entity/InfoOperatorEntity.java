package com.cloudjet.coupon.entity;

public class InfoOperatorEntity extends BaseEntity {

	/**
	 * 优惠券操作人表
	 */
	private static final long serialVersionUID = 1L;

	private String infoId;//优惠券ID
	private String operatorId;//操作人id
	private String operatorName;//操作人姓名
	private String operatorTel;//操作人电话
	private Integer type;//操作类型
	
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	
	
	
	
}
