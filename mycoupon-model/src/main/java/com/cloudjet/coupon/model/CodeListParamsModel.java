package com.cloudjet.coupon.model;

import java.io.Serializable;

public class CodeListParamsModel implements Serializable{

	/**
	 * code列表查询参数
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
	
	private String platCode;//店铺code
	private Integer codePlanStatus;//状态0，初始化；1，绑定券
	private Integer pageNo;//页码。取值范围:大于零的整数;
	private Integer pageSize;//每页条数。取值范围:大于零的整数;
	private boolean ispage;//是否分页
	
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
	public boolean isIspage() {
		return ispage;
	}
	public void setIspage(boolean ispage) {
		this.ispage = ispage;
	}
	@Override
	public String toString() {
		return "CodeListParamsModel [platCode=" + platCode + ", codePlanStatus=" + codePlanStatus + ", pageNo=" + pageNo
				+ ", pageSize=" + pageSize + ", ispage=" + ispage + "]";
	}
	
	
}
