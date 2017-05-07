package com.cloudjet.coupon.entity.dto;

import com.cloudjet.coupon.entity.BaseEntity;

public class ConvertCodeDetailEntity extends BaseEntity{

	/**
	 * 兑换码详情
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
	
	/**
	 *	券包id
	 */
	private String userBagId;
	
	/**
	 * 数量
	 */
	private Integer count;
	
	/**
	 * 优惠券id
	 */
	private String infoId;

	/**
	 * 兑换类型，0,兑换优惠券 ；  1,兑换券包
	 */
	private Integer type;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserBagId() {
		return userBagId;
	}

	public void setUserBagId(String userBagId) {
		this.userBagId = userBagId;
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
	
	
}
