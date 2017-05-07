package com.cloudjet.coupon.request;

import java.io.Serializable;

public class SendMsgParamsModel implements Serializable{

	/**
	 * 测试发送短信
	 */
	private static final long serialVersionUID = 1L;
		
	/**
	 * 电话号码
	 */
	private String tel;
	
	/**
	 * 当前券码批次
	 */
	private String codePlanId;

	public String getCodePlanId() {
		return codePlanId;
	}

	public void setCodePlanId(String codePlanId) {
		this.codePlanId = codePlanId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "SendMsgParamsModel [tel=" + tel + ", codePlanId=" + codePlanId + "]";
	}
	
}
