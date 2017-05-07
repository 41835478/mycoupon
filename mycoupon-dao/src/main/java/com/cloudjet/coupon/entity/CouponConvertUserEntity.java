package com.cloudjet.coupon.entity;

public class CouponConvertUserEntity extends BaseEntity{

	/**
	 * 兑换码使用记录
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 兑换码id
	 */
	private String codeId;
	
	/**
	 * 用户领取卡包中的id
	 */
	private String userBagId;

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getUserBagId() {
		return userBagId;
	}

	public void setUserBagId(String userBagId) {
		this.userBagId = userBagId;
	}


	
	
}
