package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;

public class ChannelModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 业务渠道编号
	 * */
	private String channelNo;
	/**
	 * 业务渠道名称
	 * */
	private String channelName;
	/**
	 * 渠道平台明细
	 * */
	
	private List<ChannelDetailModel> channelDetails;
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
	public List<ChannelDetailModel> getChannelDetails() {
		return channelDetails;
	}
	public void setChannelDetails(List<ChannelDetailModel> channelDetails) {
		this.channelDetails = channelDetails;
	}
	@Override
	public String toString() {
		return "ChannelModel [channelNo=" + channelNo + ", channelName=" + channelName + ", channelDetails="
				+ channelDetails + "]";
	}

	
	
}
