package com.cloudjet.coupon.model;

import java.io.Serializable;

public class ChannelDetailModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 业务渠道id
	 * */
	private String InfoChannelId;
	/**
	 * 渠道平台名字
	 * */
	private String channelDetailName;
	/**
	 * 渠道平台编号
	 * */
	private String channelDetailNo;
	
	public String getInfoChannelId() {
		return InfoChannelId;
	}
	public void setInfoChannelId(String infoChannelId) {
		InfoChannelId = infoChannelId;
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
	
	@Override
	public String toString() {
		return "ChannelDetailModel [channelDetailName=" + channelDetailName + "]";
	}

}
