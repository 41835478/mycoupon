package com.cloudjet.coupon.entity;

import java.io.Serializable;

public class JphUserEntity implements Serializable{

	/**
	 * 精品汇用户测试表
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;//主键
	private String tel;//手机号
	private String level;//会员等级
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	
	
}
