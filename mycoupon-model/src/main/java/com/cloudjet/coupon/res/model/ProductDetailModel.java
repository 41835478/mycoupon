package com.cloudjet.coupon.res.model;

import java.io.Serializable;

public class ProductDetailModel implements Serializable{

	/**
	 * 活动商品列表
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商铺号
	 */
	private String shopCode;
	
	/**
	 * 商铺名字
	 */
	private String shopName;
	
	/**
	 * 商品
	 */
	private String productId;

	/**
	 * 商品名字
	 */
	private String productName;
	
	/**
	 * 商品类型，0,不参与任何优惠活动；1,参与优惠活动
	 */
	private Integer type;

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Override
	public String toString() {
		return "ProductDetailModel [shopCode=" + shopCode + ", shopName=" + shopName + ", productId=" + productId
				+ ", productName=" + productName + ", type=" + type + "]";
	}
	
	

}
