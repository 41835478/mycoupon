package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;

public class SortDetailModel implements Serializable{

	/**
	 * 店铺品类明细
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商铺号
	 * */
	private String shopCode;
	
	/**
	 * 品类名称
	 */
	private String name;
	
	/**
	 * 品类id
	 */
	private String sortId;
	
	/**
	 * 品类对应的商品
	 */
	private List<String> productIds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public List<String> getProductIds() {
		return productIds;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}
		
}
