package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;

public class BindCpModel implements Serializable{

	/**
	 * code,优惠券绑定model
	 */
	private static final long serialVersionUID = 1L;
	
	private String platCode;
	private String cpId;
	private List<String> codePlanIds;
	
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public List<String> getCodePlanIds() {
		return codePlanIds;
	}
	public void setCodePlanIds(List<String> codePlanIds) {
		this.codePlanIds = codePlanIds;
	}
	@Override
	public String toString() {
		return "BindCpModel [platCode=" + platCode + ", cpId=" + cpId + ", codePlanIds=" + codePlanIds + "]";
	}
	
	
}
