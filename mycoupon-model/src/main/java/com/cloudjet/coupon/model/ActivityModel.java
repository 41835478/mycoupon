package com.cloudjet.coupon.model;

import java.io.Serializable;

public class ActivityModel implements Serializable{

	/**
	 * 口令活动model
	 */
	private static final long serialVersionUID = 1L;
	
	private String packageId;//券包id
	private String name;//活动名称
	private String slogan;//贺词
	private String details;//内容
	private String operate;//操作
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
	@Override
	public String toString() {
		return "ActivityModel [packageId=" + packageId + ", name=" + name + ", slogan=" + slogan + ", details="
				+ details + ", operate=" + operate + "]";
	}
	
	
	
	
}
