package com.cloudjet.coupon.model;

import java.io.Serializable;

public class MeanWhileMarkModel implements Serializable{

	/**
	 * 判断商品券不能与平台券同时使用标识类
	 */
	private static final long serialVersionUID = 1L;
	
	private String cpId;
	private Double par;
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public Double getPar() {
		return par;
	}
	public void setPar(Double par) {
		this.par = par;
	}
	@Override
	public String toString() {
		return "MeanWhileMarkModel [cpId=" + cpId + ", par=" + par + "]";
	}
	
	
}
