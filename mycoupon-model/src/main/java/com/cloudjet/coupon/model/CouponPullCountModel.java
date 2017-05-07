package com.cloudjet.coupon.model;

import java.io.Serializable;

public class CouponPullCountModel implements Serializable{

	/**
	 * 券领用数据模块。后期开发可能需要用到的model
	 */
	private static final long serialVersionUID = 8390187138104085019L;
	
	/**
	 * 发行量
	 */
	private Integer circulation;
	
	/**
	 * 已领取(发放)
	 */
	private Integer grantNum;

	/**
	 * 成功使用(已付款)
	 */
	private Integer costNum;
	
	/**
	 * 使用中(未付款)
	 */
	private Integer unpaidNum;
	
	/**
	 * 未使用 = 已领取(发放) - 使用中(未付款) - 成功使用(已付款)
	 */
	private Integer unusedNum;
	
	/**
	 * 使用率
	 */
	private String useRate;
	
	/**
	 * 已删除
	 */
	private Integer delNum;

	public Integer getCirculation() {
		return circulation;
	}

	public void setCirculation(Integer circulation) {
		this.circulation = circulation;
	}

	public Integer getGrantNum() {
		return grantNum;
	}

	public void setGrantNum(Integer grantNum) {
		this.grantNum = grantNum;
	}

	public Integer getCostNum() {
		return costNum;
	}

	public void setCostNum(Integer costNum) {
		this.costNum = costNum;
	}

	public Integer getUnpaidNum() {
		return unpaidNum;
	}

	public void setUnpaidNum(Integer unpaidNum) {
		this.unpaidNum = unpaidNum;
	}

	public Integer getUnusedNum() {
		return unusedNum;
	}

	public void setUnusedNum(Integer unusedNum) {
		this.unusedNum = unusedNum;
	}

	public String getUseRate() {
		return useRate;
	}

	public void setUseRate(String useRate) {
		this.useRate = useRate;
	}

	public Integer getDelNum() {
		return delNum;
	}

	public void setDelNum(Integer delNum) {
		this.delNum = delNum;
	}

	@Override
	public String toString() {
		return "CouponPullCountModel [circulation=" + circulation + ", grantNum=" + grantNum + ", costNum=" + costNum
				+ ", unpaidNum=" + unpaidNum + ", unusedNum=" + unusedNum + ", useRate=" + useRate + ", delNum="
				+ delNum + "]";
	}
		
	
}
