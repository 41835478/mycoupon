package com.cloudjet.coupon.entity;

public class LogoEntity extends BaseEntity{

	/**
	 * 券、券包logo
	 */
	private static final long serialVersionUID = 1L;

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
		return "LogoEntity [logo=" + logo + ", type=" + type + ", infoId=" + infoId + "]";
	}
	
	
}
