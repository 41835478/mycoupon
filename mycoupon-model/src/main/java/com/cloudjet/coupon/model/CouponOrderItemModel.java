package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;

public class CouponOrderItemModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 供应商id
	 * */
	private String SuppliersId;
	/**
	 * 供应商商品购买id
	 * */
	private List<CouponProductModel>  products;
	private Double price;
	
	public String getSuppliersId() {
		return SuppliersId;
	}
	public void setSuppliersId(String suppliersId) {
		SuppliersId = suppliersId;
	}
	public List<CouponProductModel> getProducts() {
		return products;
	}
	public void setProducts(List<CouponProductModel> products) {
		this.products = products;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "CouponOrderItemModel [SuppliersId=" + SuppliersId + ", products=" + products + ", price=" + price + "]";
	}
	
}
