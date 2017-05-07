package com.cloudjet.coupon.entity;

public class InfoCodeMsgTagEntity extends BaseEntity{

	/**
	 * 短信发送标识
	 */
	private static final long serialVersionUID = 1L;

	private String infoId;//券id
	private String tel;//手机号
	private String cpCode;//券码
	private Integer status;//短信发送状态，0 发送成功，1发送失败 
	
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCpCode() {
		return cpCode;
	}
	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "InfoCodeMsgTagEntity [infoId=" + infoId + ", tel=" + tel + ", cpCode=" + cpCode + ", status=" + status
				+ "]";
	}
	
	
}
