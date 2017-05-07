package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;

public class CouponOrderModel implements Serializable{
	
	/**
	 * 订单优惠券model
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 是否使用平台券 0否，1是 （进入结算页面默认为1）
	 * */
	public enum UsePlat{
		no,
		yes
	}
	/**
	 * 是否使用商品券 0否，1是（进入结算页面默认为1）
	 * */
	public enum UseProduct{
		no,
		yes
	}
	
	/**
	 * 是否使用品类券 0否，1是（进入结算页面默认为1）
	 * */
	public enum UseCategoryEnum{
		no,
		yes
	}
	
	
	/**
	 * 是否有可用的优惠券 0否，1是
	 * */
	public enum IsHave{
		no,
		yes
	}
	
	/**
	 * 主订单号
	 * */
	private String orderNo;
	/**
	 * 用户手机号
	 * */
	private String userTel;
	
	/**
	 * 用户自选优惠包券（仅仅是平台券）id
	 * */
	private String userBagId;
	/**
	 * 是否使用商品券 0否，1是（进入结算页面默认为1）
	 * */
	private Integer useProduct;
	
	/**
	 * 是否使用平台券 0否，1是 （进入结算页面默认为1）
	 * */
	private Integer usePlat;
	
	/**
	 * 是否使用品类券 0否，1是 （进入结算页面默认为1）
	 * */
	private Integer useCategory;
	
	/**
	 * 商品购买清单
	 * */
	private List<CouponProductModel>  products;
	
	/**
	 * 原总价
	 * */
	private Double price;
	
	/**
	 * 店铺code
	 * */
	private String platCode;
	
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public List<CouponProductModel> getProducts() {
		return products;
	}
	public void setProducts(List<CouponProductModel> products) {
		this.products = products;
	}
	public Integer getUseProduct() {
		return useProduct;
	}
	public void setUseProduct(Integer useProduct) {
		this.useProduct = useProduct;
	}
	public Integer getUsePlat() {
		return usePlat;
	}
	public void setUsePlat(Integer usePlat) {
		this.usePlat = usePlat;
	}
	public String getUserBagId() {
		return userBagId;
	}
	public void setUserBagId(String userBagId) {
		this.userBagId = userBagId;
	}
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	
	public Integer getUseCategory() {
		return useCategory;
	}
	public void setUseCategory(Integer useCategory) {
		this.useCategory = useCategory;
	}
	@Override
	public String toString() {
		return "CouponOrderModel [orderNo=" + orderNo + ", userTel=" + userTel + ", userBagId=" + userBagId
				+ ", useProduct=" + useProduct + ", usePlat=" + usePlat + ", useCategory=" + useCategory + ", products="
				+ products + ", price=" + price + ", platCode=" + platCode + "]";
	}
	
	
}
