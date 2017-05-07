package com.cloudjet.coupon.entity;

public class UserBagOrderEntity extends BaseEntity{

	/**
	 * 订单结算使用优惠券表
	 */
	private static final long serialVersionUID = 1L;
	
	private String orderNo;//主订单号
	private String userBagId;//用户券包id
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getUserBagId() {
		return userBagId;
	}
	public void setUserBagId(String userBagId) {
		this.userBagId = userBagId;
	}
	
	
	
	
	

}
