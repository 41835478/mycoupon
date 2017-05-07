package com.cloudjet.coupon.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShareUserBagModel implements Serializable{

	/**
	 * 分享券model推送消息队列
	 */
	private static final long serialVersionUID = 1L;
	
	private String shareTel;//分享者tel
	private String cpId;//券(包)
	private String pullTel;//领取券手机号
	private String type;//0，分享为券包id;1，券id
	private Date shareTime;//分享时间
	private List<String> platCodes;//商铺
	
	public List<String> getPlatCodes() {
		return platCodes;
	}
	public void setPlatCodes(List<String> platCodes) {
		this.platCodes = platCodes;
	}
	public String getShareTel() {
		return shareTel;
	}
	public void setShareTel(String shareTel) {
		this.shareTel = shareTel;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getPullTel() {
		return pullTel;
	}
	public void setPullTel(String pullTel) {
		this.pullTel = pullTel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getShareTime() {
		return shareTime;
	}
	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}
	@Override
	public String toString() {
		return "ShareUserBagModel [shareTel=" + shareTel + ", cpId=" + cpId + ", pullTel=" + pullTel + ", type=" + type
				+ ", shareTime=" + shareTime + ", platCodes=" + platCodes + "]";
	}
	
	
	
}
