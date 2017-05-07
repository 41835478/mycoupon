package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;

public class ProductLogModel implements Serializable{

	/**
	 * 指定商铺下的优惠券不使用优惠券,日志
	 */
	private static final long serialVersionUID = 1L;
	
	public enum ProductStatusEnum{
		/**
		 * 0,创建
		 */
		init,
		/**
		 * 1,删除
		 */
		delete
	}
	
	/**
	 * 商铺号
	 */
	private String shopCode;
	
	/**
	 * 商品id
	 */
	private List<String> productIds;

	/**
	 * 操作类型，0创建，1编辑，2删除
	 */
	private Integer type;

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public List<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ProductLogModel [shopCode=" + shopCode + ", productIds=" + productIds + ", type=" + type + "]";
	}
	
	
}
