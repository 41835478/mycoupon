package com.cloudjet.coupon.res.model;

import java.io.Serializable;
import java.util.Date;

public class CodeListResModel implements Serializable{

	/**
	 * 券码列表查询返回model
	 */
	private static final long serialVersionUID = 1L;

	public enum CodePlanStatus{
		
		/**
		 * 初始化
		 * */
		init,
		/**
		 * 已绑定
		 * */
		bind
		
	}
	
	public enum CodeMsgStatus{
		/**
		 * 未发送短信
		 */
		no,
		/**
		 * 已发送短信
		 */
		yes
	}
	
	/**
	 * 批次id
	 */
	private String codeId;
	
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
	 * 绑定的券id
	 */
	private String cpId;
	
	/**
	 * code批次创建时间
	 */
	private Date createTime;
	
	/**
	 * 优惠券名字
	 */
	private String cpName;
	
	/**
	 * 店铺名字
	 */
	private String shopName;
	
	/**
	 * 是否发送信息,0 否，1是
	 */
	private String isMsg;

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

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getCodePlanStatus() {
		return codePlanStatus;
	}

	public void setCodePlanStatus(Integer codePlanStatus) {
		this.codePlanStatus = codePlanStatus;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
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

	public String getCpId() {
		return cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "CodeListResModel [codeId=" + codeId + ", count=" + count + ", platCode=" + platCode
				+ ", codePlanStatus=" + codePlanStatus + ", cpId=" + cpId + ", createTime=" + createTime + ", cpName="
				+ cpName + ", shopName=" + shopName + ", isMsg=" + isMsg + ", type=" + type + "]";
	}

}
