package com.cloudjet.coupon.model;

import java.io.Serializable;

public class CouponProductModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商品分类 id
	 * */
	private String sort;
	/**
	 * 商品id
	 * */
	private String spuCode;
	/**
	 * 商品单价
	 * */
	private Double price;
	/**
	 * 商品购买数量
	 * */
    private Integer count;
    
    /**
     * 商品优惠金额
     * */
    private Double couponGoodPrice;
    /**
     * 使用优惠券
     * */
    private UserBagFindModel userBagFindModel;
    

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSpuCode() {
		return spuCode;
	}

	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getCouponGoodPrice() {
		//优惠金额
		if(userBagFindModel != null){
			couponGoodPrice = userBagFindModel.getPar();
		}
		return couponGoodPrice;
	}

	public void setCouponGoodPrice(Double couponGoodPrice) {
		this.couponGoodPrice = couponGoodPrice;
	}
	
	
	public UserBagFindModel getUserBagFindModel() {
		return userBagFindModel;
	}

	public void setUserBagFindModel(UserBagFindModel userBagFindModel) {
		this.userBagFindModel = userBagFindModel;
	}

	@Override
	public String toString() {
		return "CouponProductModel [sort=" + sort + ", spuCode=" + spuCode + ", price=" + price + ", count=" + count
				+ ", couponGoodPrice=" + couponGoodPrice + ", userBagFindModel=" + userBagFindModel + "]";
	}
    
	
}
