package com.cloudjet.coupon.model;

import java.io.Serializable;

public class LogoModel implements Serializable{

	/**
	 * 券、券包model
	 */
	private static final long serialVersionUID = 1L;

	public enum LogoTypeEnum{
		coupon,/** 优惠券*/
		couponPackage,   /** 券包 */
		
	}
	
	/**
	 * logo的id
	 */
	private String id;
	
	/**
	 * logo
	 */
	private String logo;
	
	/**
	 * 类型，0券；1券包
	 */
	private Integer type;
	
	/**
	 * 绑定的券id、券包id
	 */
	private String infoId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	@Override
	public String toString() {
		return "LogoModel [id=" + id + ", logo=" + logo + ", type=" + type + ", infoId=" + infoId + "]";
	}
	
	
}
