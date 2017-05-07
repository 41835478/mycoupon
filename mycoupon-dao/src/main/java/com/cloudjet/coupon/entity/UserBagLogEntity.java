package com.cloudjet.coupon.entity;

public class UserBagLogEntity extends BaseEntity{

	/**
	 * 用户券包变更日志
	 */
	private static final long serialVersionUID = 1L;

	private String userBagId;//用户券包id
	private Integer type;//券包券日志领取使用 0,未领取 1,已领取  2,已使用  3,已删除
	
	public String getUserBagId() {
		return userBagId;
	}
	public void setUserBagId(String userBagId) {
		this.userBagId = userBagId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
		
	
	
	
	
	
	
	
}
