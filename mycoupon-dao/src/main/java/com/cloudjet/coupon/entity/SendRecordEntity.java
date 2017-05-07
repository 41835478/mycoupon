package com.cloudjet.coupon.entity;

public class SendRecordEntity extends BaseEntity {

	/**
	 * 优惠券发放统计表
	 */
	private static final long serialVersionUID = 1L;

	private String infoId;//优惠券id
	private Integer type;//发放类型，发放0，补发1
	private Integer userScope;//用户范围，等级用户0，导入用户1 活动用户2
	private Integer count;//发布用户数
	private String infoOperatorId;//操作券id
	
	public String getInfoOperatorId() {
		return infoOperatorId;
	}
	public void setInfoOperatorId(String infoOperatorId) {
		this.infoOperatorId = infoOperatorId;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getUserScope() {
		return userScope;
	}
	public void setUserScope(Integer userScope) {
		this.userScope = userScope;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	
}
