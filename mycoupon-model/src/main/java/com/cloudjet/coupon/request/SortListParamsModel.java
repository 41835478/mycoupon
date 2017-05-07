package com.cloudjet.coupon.request;

import java.io.Serializable;

public class SortListParamsModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String shopCode;//商铺号
	private Integer pageNo;//页码。取值范围:大于零的整数;
	private Integer pageSize;//每页条数。取值范围:大于零的整数;
	private boolean ispage;//是否分页
	
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
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
		return "SortListParamsModel [shopCode=" + shopCode + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", ispage=" + ispage + "]";
	}

}
