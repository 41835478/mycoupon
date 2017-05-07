package com.cloudjet.coupon.entity;

import com.cloudjet.coupon.entity.BaseEntity;

public class CouponConvertCodeEntity extends BaseEntity{

	/**
	 * 券码表
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 券码
	 */
	private String code;
	
	/**
	 * 券码批次
	 */
	private String convertId;

	/**
	 * 0,未绑定(默认)；1已绑定手机号
	 */
	private Integer status;
		
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getConvertId() {
		return convertId;
	}

	public void setConvertId(String convertId) {
		this.convertId = convertId;
	}

	@Override
	public String toString() {
		return "CouponConvertCodeEntity [code=" + code + ", convertId=" + convertId + ", status=" + status + "]";
	}
	
	
}
