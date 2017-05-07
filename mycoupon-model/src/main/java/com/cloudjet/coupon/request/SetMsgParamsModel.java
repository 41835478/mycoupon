package com.cloudjet.coupon.request;

import java.io.Serializable;

public class SetMsgParamsModel implements Serializable{

	/**
	 * 设置短信模板参数
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 当前券码批次
	 */
	private String codePlanId;
	
	/**
	 * 短信里面URL
	 */
	private String msgUrl;
	
	/**
	 * 短信模板
	 */
	private String msgTemplate;

	/**
	 * 券码名称，如 "券码："
	 */
	private String param;
	
	/**
	 * 消息标示 默认jph,holiday(复星旅游)
	 */
	private String msgTag;
	
	public String getMsgTag() {
		return msgTag;
	}
	public void setMsgTag(String msgTag) {
		this.msgTag = msgTag;
	}
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getMsgUrl() {
		return msgUrl;
	}

	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl;
	}

	public String getMsgTemplate() {
		return msgTemplate;
	}

	public void setMsgTemplate(String msgTemplate) {
		this.msgTemplate = msgTemplate;
	}

	public String getCodePlanId() {
		return codePlanId;
	}

	public void setCodePlanId(String codePlanId) {
		this.codePlanId = codePlanId;
	}
	@Override
	public String toString() {
		return "SetMsgParamsModel [codePlanId=" + codePlanId + ", msgUrl=" + msgUrl + ", msgTemplate=" + msgTemplate
				+ ", param=" + param + ", msgTag=" + msgTag + "]";
	}


		
}
