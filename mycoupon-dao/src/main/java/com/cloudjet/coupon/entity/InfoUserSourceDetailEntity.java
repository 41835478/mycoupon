package com.cloudjet.coupon.entity;

public class InfoUserSourceDetailEntity extends BaseEntity {

	/**
	 * 用户资源详情表
	 */
	private static final long serialVersionUID = 1L;

	private String userSourceId;//用户来源主键id
	private String tel;//电话号码
	
	public String getUserSourceId() {
		return userSourceId;
	}
	public void setUserSourceId(String userSourceId) {
		this.userSourceId = userSourceId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
}
