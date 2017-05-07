package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;

public class UserPackagePullModel implements Serializable{

	/**
	 * 优惠券礼包领取model
	 */
	private static final long serialVersionUID = 1L;
	
	private String platCode;//店铺code
	private String tel;//电话号码
	private List<String> cpIds;
	
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public List<String> getCpIds() {
		return cpIds;
	}
	public void setCpIds(List<String> cpIds) {
		this.cpIds = cpIds;
	}
	@Override
	public String toString() {
		return "UserPackagePullModel [platCode=" + platCode + ", tel=" + tel + ", cpIds=" + cpIds + "]";
	}
	
	
	
}
