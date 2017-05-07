package com.cloudjet.coupon.model;

import java.io.Serializable;

public class PackagePullModel implements Serializable{
	/**
	 * 用户领取券包model
	 */
	private static final long serialVersionUID = 1L;

	private String platCode;//店铺code
	private String tel;//电话号码
	private String packageId;//券包id
	private String shareCode;  //分享用户手机号-券(包)id-0(券)1(包)-分享时间戳
	
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getShareCode() {
		return shareCode;
	}
	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}
	@Override
	public String toString() {
		return "PackagePullModel [platCode=" + platCode + ", tel=" + tel + ", packageId=" + packageId + ", shareCode="
				+ shareCode + "]";
	}
	
	
}
