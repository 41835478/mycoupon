package com.cloudjet.coupon.entity;

public class CouponActivityEntity extends BaseEntity{

	/**
	 * 活动实体类
	 */
	private static final long serialVersionUID = 1L;
	private String packageId;//券包id
	private String name;//活动名称
	private String slogan;//恭喜词
	private String details;//获得xx
	private String operate;//领取跳转按钮
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	
	
}
