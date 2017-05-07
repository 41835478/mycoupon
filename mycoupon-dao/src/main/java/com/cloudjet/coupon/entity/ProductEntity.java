package com.cloudjet.coupon.entity;

public class ProductEntity extends BaseEntity{

	/**
	 * 记录哪些商品不使用优惠券
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商铺号
	 */
	private String shopCode;
	
	/**
	 * 商品
	 */
	private String productId;

	/**
	 * 商品名字
	 */
	private String productName;
	
	/**
	 * 店铺名字
	 */
	private String shopName;
	
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

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

	@Override
	public String toString() {
		return "ProductEntity [shopCode=" + shopCode + ", productId=" + productId + ", productName=" + productName
				+ ", shopName=" + shopName + "]";
	}

	
}
