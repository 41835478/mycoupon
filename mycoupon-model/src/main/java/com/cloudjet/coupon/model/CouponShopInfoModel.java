package com.cloudjet.coupon.model;

import java.io.Serializable;

public class CouponShopInfoModel implements Serializable{

	/**
	 * 商铺信息
	 */
	private static final long serialVersionUID = 1L;

	private String shopName;//店铺名字
	private String platCode;//平台商铺号
	private String infoId;//优惠券

	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	@Override
	public String toString() {
		return "CouponShopInfoModel [shopName=" + shopName + ", platCode=" + platCode + ", infoId=" + infoId + "]";
	}
	
	
}
