package com.cloudjet.coupon.entity;

public class CouponConvertPlanEntity extends BaseEntity{

	/**
	 * 券码兑换批次表
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 数量
	 */
	private Integer count;
	
	/**
	 * 优惠券id或者券包id
	 */
	private String infoId;
	
	/**
	 * 兑换码兑换类型，0 优惠券，1券包
	 */
	private Integer type;
	
	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CouponConvertPlanEntity [count=" + count + ", infoId=" + infoId + ", type=" + type + "]";
	}
	
}
