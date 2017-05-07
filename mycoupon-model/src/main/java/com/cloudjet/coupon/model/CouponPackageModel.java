package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CouponPackageModel implements Serializable{

	/**
	 * 券包model
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 券包状态 0，初始化；1，绑定优惠券；2，冻结
	 * */
	public enum CouponPackageStatus{
		init,	/** 0,初始化 */
		bind,   /** 1，绑定优惠券 */
		freeze	/** 2, 冻结 */
	}
	
	/**
	 * 券包分享 0，否；1，可以
	 * */
	public enum CouponPackageIsShare{
		no,	/** 0,否 */
		yes   /** 1，是 */
	}
	
	/**
	 * 券包id
	 */
	private String packageId;
	
	/**
	 * 券包名字
	 */
	private String name;
	
	/**
	 * 券包状态 0，初始化；1，绑定优惠券；2，冻结 
	 */
	private Integer status;
	
	/**
	 * 券包领取时间
	 */
	private Date startTime;
	
	/**
	 * 券包领取结束时间
	 */
	private Date endTime;
	
	/**
	 * 商铺号
	 */
	private String platCode;
	
	/**
	 * 是否是分享券包 0，不是；1是
	 */
	private Integer isShare;
	
	/**
	 * 商铺名字
	 */
	private String shopName;
	
	/**
	 * 富文本
	 */
	private String richText;
	
	/**
	 * 券包关联的优惠券
	 */
	private List<CouponInfoModel> couponInfoModels;
	
	/**
	 * 优惠券logo
	 */
	private List<LogoModel> logos;
	
	/**
	 * 渠道描述
	 */
	private String channelText;
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public List<CouponInfoModel> getCouponInfoModels() {
		return couponInfoModels;
	}
	public void setCouponInfoModels(List<CouponInfoModel> couponInfoModels) {
		this.couponInfoModels = couponInfoModels;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsShare() {
		return isShare;
	}
	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}
	public List<LogoModel> getLogos() {
		return logos;
	}
	public void setLogos(List<LogoModel> logos) {
		this.logos = logos;
	}
	public String getRichText() {
		return richText;
	}
	public void setRichText(String richText) {
		this.richText = richText;
	}
	
	public String getChannelText() {
		return channelText;
	}
	public void setChannelText(String channelText) {
		this.channelText = channelText;
	}
	
	@Override
	public String toString() {
		return "CouponPackageModel [packageId=" + packageId + ", name=" + name + ", status=" + status + ", startTime="
				+ startTime + ", endTime=" + endTime + ", platCode=" + platCode + ", isShare=" + isShare + ", shopName="
				+ shopName + ", richText=" + richText + ", couponInfoModels=" + couponInfoModels + ", logos=" + logos
				+ ", channelText=" + channelText + "]";
	}
	

}
