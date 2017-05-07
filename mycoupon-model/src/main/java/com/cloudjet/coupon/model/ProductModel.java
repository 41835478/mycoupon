package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.Map;

public class ProductModel implements Serializable{

	/**
	 * 指定商铺下的优惠券不使用优惠券
	 */
	private static final long serialVersionUID = 1L;
		
	/**
	 * 店铺
	 */
	private String shopCode;
	
	/**
	 * 店铺名字
	 */
	private String shopName;
	
	/**
	 * 商品id, 商品名字
	 */
	private Map<String, String> products; 
	
	
	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public Map<String, String> getProducts() {
		return products;
	}

	public void setProducts(Map<String, String> products) {
		this.products = products;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Override
	public String toString() {
		return "ProductModel [shopCode=" + shopCode + ", shopName=" + shopName + ", products=" + products + "]";
	}

	
	
}
