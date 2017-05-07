package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;

public class CouponOrderResModel implements Serializable{

	/**
	 * 下单返回订单优惠数据model
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单的总金额（未优惠）
	 * */
	private Double totalOrigin = 0.00;
	
	/**
	 * 订单优惠总金额
	 * */
	private Double couponPrice = 0.00;
	
	/**
	 * 商品优惠总金额
	 * */
	private Double couponProductBestPrice = 0.00;
	
	/**
	 * 品类券优惠总金额
	 * */
	private Double couponCategoryBestPrice = 0.00;
	/**
	 * 全场券优惠总金额
	 * */
	private Double couponPlatBestPrice = 0.00;
	
	/**
	 * 订单优惠最优总金额
	 * */
	private Double couponBestPrice = 0.00;
	
	/**
	 * 用户自选券包id
	 * */
	private UserBagModel userBagSelfModel;
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
	 * 是否有可使用的商品优惠券
	 * */
	private Integer isHaveProduct;
	
	/**
	 * 是否有可使用的品类优惠券
	 * */
	private Integer isHaveCategory;
	
	/**
	 * 是否有可使用的平台优惠券
	 * */
	private Integer isHavePlat;
	/**
	 * 单品优惠返回
	 * */
	private List<CouponProductModel> couponProducts;
	
	/**
	 * 可用商品优惠券
	 * */
	private List<UserBagModel> userBagProducts;
	
	/**
	 * 可用平台优惠券列表
	 * */
	private List<UserBagModel> userBagPlats;
	
	/**
	 * 可用品类优惠券列表
	 * */
	private List<UserBagModel> userBagCategorys;

	public Double getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(Double couponPrice) {
		this.couponPrice = couponPrice;
	}

	public List<CouponProductModel> getCouponProducts() {
		return couponProducts;
	}

	public void setCouponProducts(List<CouponProductModel> couponProducts) {
		this.couponProducts = couponProducts;
	}
	public List<UserBagModel> getUserBagProducts() {
		return userBagProducts;
	}

	public void setUserBagProducts(List<UserBagModel> userBagProducts) {
		this.userBagProducts = userBagProducts;
	}

	public List<UserBagModel> getUserBagPlats() {
		return userBagPlats;
	}

	public void setUserBagPlats(List<UserBagModel> userBagPlats) {
		this.userBagPlats = userBagPlats;
	}

	
	public UserBagModel getUserBagSelfModel() {
		return userBagSelfModel;
	}

	public void setUserBagSelfModel(UserBagModel userBagSelfModel) {
		this.userBagSelfModel = userBagSelfModel;
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

	public Double getCouponBestPrice() {
		return couponBestPrice;
	}

	public void setCouponBestPrice(Double couponBestPrice) {
		this.couponBestPrice = couponBestPrice;
	}

	public Double getCouponProductBestPrice() {
		return couponProductBestPrice;
	}

	public void setCouponProductBestPrice(Double couponProductBestPrice) {
		this.couponProductBestPrice = couponProductBestPrice;
	}

	public Double getCouponPlatBestPrice() {
		return couponPlatBestPrice;
	}

	public void setCouponPlatBestPrice(Double couponPlatBestPrice) {
		this.couponPlatBestPrice = couponPlatBestPrice;
	}

	public Integer getIsHaveProduct() {
		return isHaveProduct;
	}

	public void setIsHaveProduct(Integer isHaveProduct) {
		this.isHaveProduct = isHaveProduct;
	}

	public Integer getIsHavePlat() {
		return isHavePlat;
	}

	public void setIsHavePlat(Integer isHavePlat) {
		this.isHavePlat = isHavePlat;
	}

	public Double getTotalOrigin() {
		return totalOrigin;
	}

	public void setTotalOrigin(Double totalOrigin) {
		this.totalOrigin = totalOrigin;
	}
	
	public Double getCouponCategoryBestPrice() {
		return couponCategoryBestPrice;
	}

	public void setCouponCategoryBestPrice(Double couponCategoryBestPrice) {
		this.couponCategoryBestPrice = couponCategoryBestPrice;
	}

	public Integer getUseCategory() {
		return useCategory;
	}

	public void setUseCategory(Integer useCategory) {
		this.useCategory = useCategory;
	}

	public Integer getIsHaveCategory() {
		return isHaveCategory;
	}

	public void setIsHaveCategory(Integer isHaveCategory) {
		this.isHaveCategory = isHaveCategory;
	}
	
	public List<UserBagModel> getUserBagCategorys() {
		return userBagCategorys;
	}

	public void setUserBagCategorys(List<UserBagModel> userBagCategorys) {
		this.userBagCategorys = userBagCategorys;
	}

	@Override
	public String toString() {
		return "CouponOrderResModel [totalOrigin=" + totalOrigin + ", couponPrice=" + couponPrice
				+ ", couponProductBestPrice=" + couponProductBestPrice + ", couponCategoryBestPrice="
				+ couponCategoryBestPrice + ", couponPlatBestPrice=" + couponPlatBestPrice + ", couponBestPrice="
				+ couponBestPrice + ", userBagSelfModel=" + userBagSelfModel + ", useProduct=" + useProduct
				+ ", usePlat=" + usePlat + ", useCategory=" + useCategory + ", isHaveProduct=" + isHaveProduct
				+ ", isHaveCategory=" + isHaveCategory + ", isHavePlat=" + isHavePlat + ", couponProducts="
				+ couponProducts + ", userBagProducts=" + userBagProducts + ", userBagPlats=" + userBagPlats
				+ ", userBagCategorys=" + userBagCategorys + "]";
	}
	
	
}
