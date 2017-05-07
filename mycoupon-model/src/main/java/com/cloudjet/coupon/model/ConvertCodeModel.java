package com.cloudjet.coupon.model;

import java.io.Serializable;

public class ConvertCodeModel implements Serializable{

	/**
	 * 兑换码model
	 */
	private static final long serialVersionUID = 1L;

	public enum ConvertTypeEnum{
		/** 
		 * 0,兑换优惠券
		 *  */
		coupon, 
		
		/** 
		 * 1,兑换券包
		 *  */
		couponPackage 
	}
	
	/**
	 * 券码批次id
	 */
	private String convertPlanId; 
	
	/**
	 * 券或者券包id
	 */
	private String infoId;
	
	/**
	 * 兑换类型，0,兑换优惠券 ；  1,兑换券包
	 */
	private Integer type;

	public String getConvertPlanId() {
		return convertPlanId;
	}

	public void setConvertPlanId(String convertPlanId) {
		this.convertPlanId = convertPlanId;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ConvertCodeModel [convertPlanId=" + convertPlanId + ", infoId=" + infoId + ", type=" + type + "]";
	}
	
	
}
