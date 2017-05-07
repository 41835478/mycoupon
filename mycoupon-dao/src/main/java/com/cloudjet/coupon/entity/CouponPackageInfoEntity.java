package com.cloudjet.coupon.entity;

public class CouponPackageInfoEntity extends BaseEntity{

	/**
	 * 券包详情，绑定的优惠券
	 */
	private static final long serialVersionUID = 1L;

	private String infoId;//优惠券ID
	private String packageId;//券包ID
	
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	
	
	
}
