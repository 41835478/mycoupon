package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PackageEditModel implements Serializable{

	/**
	 * 券包编辑model
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
	
	public enum PackageIsShare{
		/**
		 * 不是分享券包
		 * */
		no,
		/**
		 * 是
		 * */
		yes
	}
	
	/**
	 * 优惠券logo
	 */
	private List<LogoModel> logos;
	
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
	 * 是否是分享券包 0 不是，1是
	 */
	private Integer isShare;
	
	/**
	 * 券包关联的优惠券
	 */
	private List<String> infoIds;
 
	/**
	 * 商铺号
	 */
	private String platCode;
	
	/**
	 * 商铺名字
	 */
	private String shopName;
	
	/**
	 * 富文本
	 */
	private String richText;
	
	/**
	 * 渠道描述
	 */
	private String channelText;
	
	/**
	 * 券包logo
	 */
	public List<LogoModel> getLogos() {
		return logos;
	}
	public void setLogos(List<LogoModel> logos) {
		this.logos = logos;
	}
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getIsShare() {
		return isShare;
	}
	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
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
	public List<String> getInfoIds() {
		return infoIds;
	}
	public void setInfoIds(List<String> infoIds) {
		this.infoIds = infoIds;
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
		return "PackageEditModel [logos=" + logos + ", packageId=" + packageId + ", name=" + name + ", status=" + status
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", isShare=" + isShare + ", infoIds=" + infoIds
				+ ", platCode=" + platCode + ", shopName=" + shopName + ", richText=" + richText + ", channelText="
				+ channelText + "]";
	}

	
}
