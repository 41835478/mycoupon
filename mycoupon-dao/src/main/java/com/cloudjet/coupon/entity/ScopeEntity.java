package com.cloudjet.coupon.entity;

public class ScopeEntity extends BaseEntity{

	/**
	 * 优惠券使用范围配置表
	 */
	private static final long serialVersionUID = 1L;

	private String name;//券名称
	private Integer type;//优惠券使用范围类型0,全场；1,分类；2,商品 3,供应商
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
}
