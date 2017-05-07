package com.cloudjet.coupon.entity;

public class InfoChannelEntity extends BaseEntity{

	/**
	 * 优惠券渠道表
	 */
	private static final long serialVersionUID = 1L;

	private String infoId;//优惠券id
	private String channelNo;//渠道id编号
	private String channelName;//渠道名称  精品汇，星之灵
	
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
		
	
	
	
	
	
}
