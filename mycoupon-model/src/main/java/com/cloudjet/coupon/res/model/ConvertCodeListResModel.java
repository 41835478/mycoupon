package com.cloudjet.coupon.res.model;

import java.io.Serializable;

public class ConvertCodeListResModel implements Serializable{

	/**
	 * 兑换码列表查询返回model
	 */
	private static final long serialVersionUID = 1L;

	public enum ConertTypeEnum{
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
	 * 批次id
	 */
	private String convertPlanId;
	
	/**
	 * 数量
	 */
	private Integer count;
		
	/**
	 * 优惠券名字、券包名字
	 */
	private String cpName;
	
	/**
	 * 优惠券id、券包id
	 */
	private String infoId;
	
	/**
	 * 兑换类型
	 */
	private Integer type;
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getConvertPlanId() {
		return convertPlanId;
	}

	public void setConvertPlanId(String convertPlanId) {
		this.convertPlanId = convertPlanId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ConvertCodeListResModel [convertPlanId=" + convertPlanId + ", count=" + count + ", cpName=" + cpName
				+ ", infoId=" + infoId + ", type=" + type + "]";
	}

	

}

