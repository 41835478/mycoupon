package com.cloudjet.coupon.request;

import java.io.Serializable;

public class CouponPullModel implements Serializable{

	/**
	 * mq 领取优惠券model
	 */
	private static final long serialVersionUID = 1L;

	private String cpId;
	
	private String tel;

	public String getCpId() {
		return cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "CouponPullModel [cpId=" + cpId + ", tel=" + tel + "]";
	}
	
	
}
