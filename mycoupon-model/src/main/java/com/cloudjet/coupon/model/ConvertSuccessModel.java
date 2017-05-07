package com.cloudjet.coupon.model;

import java.io.Serializable;

public class ConvertSuccessModel implements Serializable{

	/**
	 * 兑换成功返回model
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 券或者券包id
	 */
	private String infoId;
	
	/**
	 * 兑换类型，0,兑换优惠券 ；  1,兑换券包
	 */
	private Integer type;

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
		return "ConvertSuccessModel [infoId=" + infoId + ", type=" + type + ", shareCodeResModel=" + "]";
	}

}
