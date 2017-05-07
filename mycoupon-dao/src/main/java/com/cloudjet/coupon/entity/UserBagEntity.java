package com.cloudjet.coupon.entity;

public class UserBagEntity extends BaseEntity{

	/**
	 *  用户卡券包
	 */
	private static final long serialVersionUID = 1L;

	private String userTel; //用户手机号
	private String infoId; //优惠劵id
	private Integer status; //用户券包状态0,已领取,被发放  1,已消费  2,已删除
	
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
