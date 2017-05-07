package com.cloudjet.coupon.entity;

public class InfoChannelDetailEntity extends BaseEntity{

	/**
	 * 渠道详情表
	 */
	private static final long serialVersionUID = 1L;

	private String infoChannelId; //渠道id
	private String channelDetailName; //业务渠道下面的明细例如是精品下面的微信，钉钉，支付宝等
	private String channelDetailNo; //明细编号
	
	public String getInfoChannelId() {
		return infoChannelId;
	}
	public void setInfoChannelId(String infoChannelId) {
		this.infoChannelId = infoChannelId;
	}
	public String getChannelDetailName() {
		return channelDetailName;
	}
	public void setChannelDetailName(String channelDetailName) {
		this.channelDetailName = channelDetailName;
	}
	public String getChannelDetailNo() {
		return channelDetailNo;
	}
	public void setChannelDetailNo(String channelDetailNo) {
		this.channelDetailNo = channelDetailNo;
	}
	
	
	
}
