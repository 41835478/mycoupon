package com.cloudjet.coupon.entity.dto;

import com.cloudjet.coupon.entity.BaseEntity;

public class CodeListEntity extends BaseEntity{

	/**
	 * 查看code批次列表 bean
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * code码数量
	 */
	private int count;
	
	/**
	 * 店铺code
	 */
	private String platCode;

	/**
	 * 状态0，初始化；1，绑定券 
	 */
	private Integer codePlanStatus;

	/**
	 * 券Id
	 */
	private String cpId;

	/**
	 * 优惠券名字
	 */
	private String cpName;

	/**
	 * 店铺名字
	 */
	private String shopName;

	/**
	 * 是否发送短信
	 */
	private String isMsg;
	
	/**
	 * 短信标示 默认 精品汇 ;旅游 holiday
	 * */
	private String msgTag;
	
	/**
	 * 导入的券码类型，0 通用 ， 1 链接
	 */
	private Integer type;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getIsMsg() {
		return isMsg;
	}
	public void setIsMsg(String isMsg) {
		this.isMsg = isMsg;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public Integer getCodePlanStatus() {
		return codePlanStatus;
	}
	public void setCodePlanStatus(Integer codePlanStatus) {
		this.codePlanStatus = codePlanStatus;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	
	public String getMsgTag() {
		return msgTag;
	}
	public void setMsgTag(String msgTag) {
		this.msgTag = msgTag;
	}
	@Override
	public String toString() {
		return "CodeListEntity [count=" + count + ", platCode=" + platCode + ", codePlanStatus=" + codePlanStatus
				+ ", cpId=" + cpId + ", cpName=" + cpName + ", shopName=" + shopName + ", isMsg=" + isMsg + ", msgTag="
				+ msgTag + ", type=" + type + "]";
	}
	
	
}
