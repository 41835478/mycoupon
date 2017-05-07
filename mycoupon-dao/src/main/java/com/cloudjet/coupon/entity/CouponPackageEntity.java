package com.cloudjet.coupon.entity;

import java.util.Date;

public class CouponPackageEntity extends BaseEntity{

	/**
	 * 优惠券的券包
	 */
	private static final long serialVersionUID = 1L;

	private String name;//券包名字
	private String platCode;//商铺号
	private Integer status;//券包状态
	private Date startTime;//券包领取时间
	private Date endTime;//券包领取结束时间
	private Integer isShare;//是否是分享券包 0 不是，1是
	private String shopName;//商铺名字
	private String richText;//富文本
	private String channelText;// 渠道描述
	public String getRichText() {
		return richText;
	}
	public void setRichText(String richText) {
		this.richText = richText;
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
	public String getChannelText() {
		return channelText;
	}
	public void setChannelText(String channelText) {
		this.channelText = channelText;
	}
	
	
}
