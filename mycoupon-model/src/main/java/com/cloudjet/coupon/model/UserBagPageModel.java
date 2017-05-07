package com.cloudjet.coupon.model;

import java.io.Serializable;

public class UserBagPageModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tel;//电话号码
	private Integer costType;// 用户券包状态，0，线上券  1,线下券
	private Integer pageNo; //页码。取值范围:大于零的整数; 默认值:1
	private Integer pageSize; //每页条数。取值范围:大于零的整数; 默认值:40;最大值:100
	private String  platCode;//用户当前店铺code
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public Integer getCostType() {
		return costType;
	}
	public void setCostType(Integer costType) {
		this.costType = costType;
	}
	@Override
	public String toString() {
		return "UserBagPageModel [tel=" + tel + ", costType=" + costType + ", pageNo=" + pageNo + ", pageSize="
				+ pageSize + ", platCode=" + platCode + "]";
	}
	
	
}
