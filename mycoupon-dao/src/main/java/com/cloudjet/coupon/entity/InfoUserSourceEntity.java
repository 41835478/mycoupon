package com.cloudjet.coupon.entity;

public class InfoUserSourceEntity extends BaseEntity{

	/**
	 * 用户来源表
	 */
	private static final long serialVersionUID = 1L;

	private String infoId;//优惠券主键id
	private Integer type;//等级 level 0,导入 1
	private String param;//参数名
	private Integer way;//用户领取方式
	
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getWay() {
		return way;
	}
	public void setWay(Integer way) {
		this.way = way;
	}
	
	
	
}
