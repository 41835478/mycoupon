package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PackageInitModel implements Serializable{

	/**
	 * 券包初始化model
	 */
	private static final long serialVersionUID = 1L;

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
	 * 商铺名字
	 */
	private String platCode;
	
	/**
	 * 券包名字
	 */
	private String name;
	
	/**
	 * 是否是分享券包 0 不是，1是
	 */
	private Integer isShare;
	
	/**
	 * 券包领取时间
	 */
	private Date startTime;
	
	/**
	 * 券包领取结束时间
	 */
	private Date endTime;
	
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
	
	public String getChannelText() {
		return channelText;
	}
	public void setChannelText(String channelText) {
		this.channelText = channelText;
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
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	@Override
	public String toString() {
		return "PackageInitModel [logos=" + logos + ", platCode=" + platCode + ", name=" + name + ", isShare=" + isShare
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", shopName=" + shopName + ", richText="
				+ richText + ", channelText=" + channelText + "]";
	}
	
	
}
