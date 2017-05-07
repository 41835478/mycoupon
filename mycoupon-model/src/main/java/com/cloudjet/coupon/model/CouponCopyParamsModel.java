package com.cloudjet.coupon.model;

import java.io.Serializable;

public class CouponCopyParamsModel implements Serializable{

	/**
	 * 优惠券复制参数model
	 */
	private static final long serialVersionUID = 1L;

	private String cpId;//要复制的优惠券的id
	private String operatorName;//操作人名字
	private String operatorTel;//操作人电话
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
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
		return "CouponCopyParamsModel [cpId=" + cpId + ", operatorName=" + operatorName + ", operatorTel=" + operatorTel
				+ ", userId=" + userId + "]";
	}
	
	
}
