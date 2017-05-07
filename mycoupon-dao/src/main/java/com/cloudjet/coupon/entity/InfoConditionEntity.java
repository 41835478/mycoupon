package com.cloudjet.coupon.entity;

public class InfoConditionEntity extends BaseEntity{

	/**
	 * 优惠券使用条件
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 优惠券主键
	 */
	private String infoId;
	
	/**
	 * 优惠券类型   zero,无门槛；reduce,满减；discount,折扣券；
	 * 
	 * */
	private String type;
	/**
	 * 面值 折扣
	 * */
	private Double par;
	/**
	 * 区间金额开始
	 * */
	private Double startFee;
	/**
	 * 区间金额 结束
	 * */
	private Double endFee;

	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getStartFee() {
		return startFee;
	}
	
	public Double getPar() {
		return par;
	}
	public void setPar(Double par) {
		this.par = par;
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
	
	
	
	
}
