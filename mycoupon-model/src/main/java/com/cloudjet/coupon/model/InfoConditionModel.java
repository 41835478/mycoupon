package com.cloudjet.coupon.model;

import java.io.Serializable;

public class InfoConditionModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 优惠券类型   zero,无门槛；reduce,满减；discount,折扣券；
	 * 
	 * */
	private String type;
	/**
	 * 满减，折扣参数
	 * */
	private Double par;
	/**
	 * 区间
	 * */
	private Double startFee;
	private Double endFee;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Double getPar() {
		return par;
	}
	public void setPar(Double par) {
		this.par = par;
	}
	public Double getStartFee() {
		return startFee;
	}
	public void setStartFee(Double startFee) {
		this.startFee = startFee;
	}
	public Double getEndFee() {
		return endFee;
	}
	public void setEndFee(Double endFee) {
		this.endFee = endFee;
	}
	@Override
	public String toString() {
		return "InfoConditionModel [type=" + type + ", par=" + par + ", startFee=" + startFee + ", endFee=" + endFee
				+ "]";
	}
		
}
