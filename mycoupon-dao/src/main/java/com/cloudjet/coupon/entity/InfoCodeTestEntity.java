package com.cloudjet.coupon.entity;

public class InfoCodeTestEntity extends BaseEntity{

	/**
	 * 测试发送短信
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 手机号
	 */
	private String tel;
	
	/**
	 * 测试的券码批次
	 */
	private String codePlanId;

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCodePlanId() {
		return codePlanId;
	}

	public void setCodePlanId(String codePlanId) {
		this.codePlanId = codePlanId;
	}

	@Override
	public String toString() {
		return "InfoCodeTestEntity [tel=" + tel + ", codePlanId=" + codePlanId + "]";
	}
	
	
}
