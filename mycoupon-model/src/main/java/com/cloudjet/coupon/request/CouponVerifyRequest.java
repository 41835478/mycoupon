package com.cloudjet.coupon.request;

import java.io.Serializable;
import java.util.List;

/**
 * 线下券核销实体类
 */

public class CouponVerifyRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 优惠券id
	 * */
	private List<String> userBagIds;
	/**
	 * 手机号
	 * */
	private String tel;
	/**
	 * 订单号
	 * */
	private String orderNo;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public List<String> getUserBagIds() {
		return userBagIds;
	}
	public void setUserBagIds(List<String> userBagIds) {
		this.userBagIds = userBagIds;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	
}
