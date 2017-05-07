package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserBagFindModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userBagId;//券包id
	private String cpId; //优惠券主键
	private String cpName;//优惠劵名字
	private Date beginTime;//优惠劵使用日期
	private Date dueTime;//优惠劵使用日期
	private String type;//优惠券优惠类型 zero，无门槛；reduce，满减 ；discount，折扣券
	private String rule;//优惠券使用规则
	private String memo;//优惠券备注
	private String conditionId;//优惠券条件id
	
	private int couponType;//优惠券类型 0,平台优惠券；1商品优惠券
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
	
	/**
	 * 
	 * 券共同使用 小 范围 0，否；1,是
	 * 
	 * */
	private Integer meanwhile;
	
	/**
	 * 
	 * 券共同使用 小 范围 0，否；1,是
	 * 
	 * */
	private Integer meantime;
	
	/**
	 * 当优惠券是商品优惠券包括的商品列表
	 * */
	private List<String> productIds;
	/**
	 * 最终使用的商品id
	 * */
	private String productId;
	/**
	 * 商品的分类
	 * */
	private String sortId;
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

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getDueTime() {
		return dueTime;
	}

	public void setDueTime(Date dueTime) {
		this.dueTime = dueTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public int getCouponType() {
		return couponType;
	}

	public void setCouponType(int couponType) {
		this.couponType = couponType;
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

	public List<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

	public String getUserBagId() {
		return userBagId;
	}

	public void setUserBagId(String userBagId) {
		this.userBagId = userBagId;
	}

	public Integer getMeanwhile() {
		return meanwhile;
	}

	public void setMeanwhile(Integer meanwhile) {
		this.meanwhile = meanwhile;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	
	public Integer getMeantime() {
		return meantime;
	}

	public void setMeantime(Integer meantime) {
		this.meantime = meantime;
	}

	@Override
	public String toString() {
		return "UserBagFindModel [userBagId=" + userBagId + ", cpId=" + cpId + ", cpName=" + cpName + ", beginTime="
				+ beginTime + ", dueTime=" + dueTime + ", type=" + type + ", rule=" + rule + ", memo=" + memo
				+ ", conditionId=" + conditionId + ", couponType=" + couponType + ", par=" + par + ", startFee="
				+ startFee + ", endFee=" + endFee + ", meanwhile=" + meanwhile + ", meantime=" + meantime
				+ ", productIds=" + productIds + ", productId=" + productId + ", sortId=" + sortId + "]";
	}
	


}
