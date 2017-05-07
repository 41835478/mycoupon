package com.cloudjet.coupon.entity.dto;

import com.cloudjet.coupon.entity.BaseEntity;

public class ProductDetailEntity extends BaseEntity{

	/**
	 * 商品详情
	 */
	private static final long serialVersionUID = 1L;

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

	@Override
	public String toString() {
		return "ProductDetailEntity [shopCode=" + shopCode + ", sortId=" + sortId + ", name=" + name + ", productId="
				+ productId + "]";
	}
	
	
}
