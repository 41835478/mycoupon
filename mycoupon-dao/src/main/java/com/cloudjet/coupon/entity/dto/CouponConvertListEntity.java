package com.cloudjet.coupon.entity.dto;

import com.cloudjet.coupon.entity.BaseEntity;

public class CouponConvertListEntity extends BaseEntity{

	/**
	 * 券码兑换批次表
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 数量
	 */
	private Integer count;
	
	/**
	 * 优惠券id
	 */
	private String infoId;
	
	/**
	 * 优惠券名字
	 */
	private String cpName;

	/**
	 * 券包名字
	 */
	private String packageName;
	
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
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	
}
