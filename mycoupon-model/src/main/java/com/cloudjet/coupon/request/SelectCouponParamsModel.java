package com.cloudjet.coupon.request;

import java.io.Serializable;

public class SelectCouponParamsModel implements Serializable{

	/**
	 * 下拉选择一张未发布优惠券，参数model
	 */
	private static final long serialVersionUID = 1L;

	public enum CouponIsCode{
		/**
		 * 不是券码券
		 */
		no,
		/**
		 * 券码优惠券
		 * */
		yes
	}
	
	/**
	 * 优惠券类型
	 * */
	public enum CouponInfoType{
		platform,/** 平台优惠券*/
		goods   /** 商品优惠券 */
	}
	
	/**
	 * 优惠券消费场景类型 0，线上；1线下
	 * */
	public enum CouponInfoCostType{
		online,/** 线上*/
		offline   /** 线下  */
	}
	
	/**
	 * 优惠券类型
	 * 0,平台优惠券；1商品优惠券
	 * */
	private Integer type;
	
	/**
	 * 是否是券码优惠券  0,不是； 1,是 
	 * */
	private Integer isCode;
	
	/**
	 * 优惠券消费类型
	 * 0,线上；1线下
	 * */
	private Integer costType;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsCode() {
		return isCode;
	}

	public void setIsCode(Integer isCode) {
		this.isCode = isCode;
	}

	public Integer getCostType() {
		return costType;
	}

	public void setCostType(Integer costType) {
		this.costType = costType;
	}

	@Override
	public String toString() {
		return "SelectCouponParamsModel [type=" + type + ", isCode=" + isCode + ", costType=" + costType + "]";
	}
	
}
