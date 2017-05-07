package com.cloudjet.coupon.request;

import java.io.Serializable;

public class ProductSortListModel implements Serializable{

	/**
	 * 查询品类列表
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
	private Integer pageNo;//页码。取值范围:大于零的整数;
	private Integer pageSize;//每页条数。取值范围:大于零的整数;
	private Integer pagination;//分页参数，0 不分页，1分页
	private String name;//分类名字
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public Integer getPagination() {
		return pagination;
	}
	public void setPagination(Integer pagination) {
		this.pagination = pagination;
	}
	
	@Override
	public String toString() {
		return "ProductSortListModel [shopCode=" + shopCode + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", pagination=" + pagination + ", name=" + name + "]";
	}
	
	

}
