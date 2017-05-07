package com.cloudjet.coupon.model;

import java.io.Serializable;

public class ProductSortLogModel implements Serializable{

	/**
	 * 分类操作日志
	 */
	private static final long serialVersionUID = 1L;
	
	public enum SortLogTypeEnum{
		/**
		 * 创建
		 */
		init, 
		/**
		 * 编辑
		 */
		edit,
		/**
		 * 删除
		 */
		delete
	}

	/**
	 * 商铺号
	 */
	private String shopCode;
	
	/**
	 * 分类id
	 */
	private String sortId;
	
	/**
	 * 分类名
	 */
	private String name;
	
	/**
	 * 商品id 
	 */
	private String productId;
	
	
	/**
	 * 操作类型，0 创建 ，1编辑 ，2删除
	 */
	public Integer type;


	public String getShopCode() {
		return shopCode;
	}


	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}


	public String getSortId() {
		return sortId;
	}


	public void setSortId(String sortId) {
		this.sortId = sortId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	@Override
	public String toString() {
		return "ProductSortLogModel [shopCode=" + shopCode + ", sortId=" + sortId + ", name=" + name + ", productId="
				+ productId + ", type=" + type + "]";
	}
	
	
	
}
