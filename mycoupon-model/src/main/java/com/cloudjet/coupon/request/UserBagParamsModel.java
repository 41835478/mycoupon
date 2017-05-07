package com.cloudjet.coupon.request;

import java.io.Serializable;

public class UserBagParamsModel implements Serializable{

	/**
	 * 查询用户券包信息参数
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 电话号码
	 */
	private String tel;
	
	/**
	 * 优惠券名字
	 */
	private String cpName;

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	@Override
	public String toString() {
		return "UserBagParamsModel [tel=" + tel + ", cpName=" + cpName + "]";
	}
	
	
}
