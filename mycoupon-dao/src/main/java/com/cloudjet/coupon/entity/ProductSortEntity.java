package com.cloudjet.coupon.entity;

public class ProductSortEntity extends BaseEntity{

	/**
	 * 分类记录
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商铺号
	 */
	private String shopCode;
	
	/**
	 * 分类名
	 */
	private String name;

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ProductSortEntity [shopCode=" + shopCode + ", name=" + name + "]";
	}
	
}
