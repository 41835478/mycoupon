package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SortProductModel implements Serializable{

	/**
	 * 分类model
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商铺号
	 * */
	private String shopCode;
	/**
	 * String 分类名字
	 * List<String> 商品id集合
	 * */
	private Map<String,List<String>> productSortIdMaps;
	
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public Map<String, List<String>> getProductSortIdMaps() {
		return productSortIdMaps;
	}
	public void setProductSortIdMaps(Map<String, List<String>> productSortIdMaps) {
		this.productSortIdMaps = productSortIdMaps;
	}
	@Override
	public String toString() {
		return "SortProductModel [shopCode=" + shopCode + ", productSortIdMaps=" + productSortIdMaps + "]";
	}
	
	
}
