package com.cloudjet.coupon.request;

import java.io.Serializable;

public class ProductListParamsModel implements Serializable{

	/**
	 * 查看店铺商品列表参数
	 */
	private static final long serialVersionUID = 1L;

	public enum PaginationEnum{
		/**
		 * 不分页
		 */
		no,
		/**
		 * 分页
		 */
		yes
	}
	
	private String shopCode;//商铺号
	private String shopName;//商品名字
	private String productName;//商品名字
	private Integer pageNo;//页码。取值范围:大于零的整数;
	private Integer pageSize;//每页条数。取值范围:大于零的整数;
	private Integer pagination;//分页参数，0 不分页，1分页
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public Integer getPagination() {
		return pagination;
	}
	public void setPagination(Integer pagination) {
		this.pagination = pagination;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	@Override
	public String toString() {
		return "ProductListParamsModel [shopCode=" + shopCode + ", shopName=" + shopName + ", productName="
				+ productName + ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", pagination=" + pagination + "]";
	}
	
	
	
}
