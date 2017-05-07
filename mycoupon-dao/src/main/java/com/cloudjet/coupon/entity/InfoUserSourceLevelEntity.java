package com.cloudjet.coupon.entity;

public class InfoUserSourceLevelEntity extends BaseEntity{

	/**
	 * 用户资源等级表
	 */
	private static final long serialVersionUID = 1L;

	private String userSourceId;//选择用户来源id
	private String level;//等级
	public String getUserSourceId() {
		return userSourceId;
	}
	public void setUserSourceId(String userSourceId) {
		this.userSourceId = userSourceId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	
	
	
}
