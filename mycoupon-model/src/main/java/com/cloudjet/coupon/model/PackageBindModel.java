package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;

public class PackageBindModel implements Serializable{

	/**
	 * 券包绑定优惠券model
	 */
	private static final long serialVersionUID = 1L;
	
	private String packageId;
	private List<String> infoIds;//券包关联的优惠券
	
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public List<String> getInfoIds() {
		return infoIds;
	}
	public void setInfoIds(List<String> infoIds) {
		this.infoIds = infoIds;
	}
	@Override
	public String toString() {
		return "PackageBindModel [packageId=" + packageId + ", infoIds=" + infoIds + "]";
	}
	
}
