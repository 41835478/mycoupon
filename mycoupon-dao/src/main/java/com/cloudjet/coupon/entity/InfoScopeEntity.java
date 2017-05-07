package com.cloudjet.coupon.entity;

public class InfoScopeEntity extends BaseEntity {
	/**
	 * 优惠券使用范围
	 */
	private static final long serialVersionUID = 1L;

	private String infoId;//优惠券ID
	private Integer scopeType;//0,全场券；1，分类卷；2，商品券；3，供应商券
	private String param;//供应商id,分类id，商品id
	
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	
	public Integer getScopeType() {
		return scopeType;
	}
	public void setScopeType(Integer scopeType) {
		this.scopeType = scopeType;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
}
