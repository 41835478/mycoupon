package com.cloudjet.coupon.request;

import java.io.Serializable;
import java.util.List;

public class UserSendRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 优惠券id
	 * */
	private String cpId;
	/**
	 * 发放用户类型选择 0，等级类型选择；1，导入用户方式；2 通用
	 * */
	private Integer type;
	
	/**
	 * 0，发放用户；1，用户领取（仅仅用作默认用户获取券方式）
	 * */
	private Integer way;
	
	/**
	 * 用户等级 选择列表
	 * */
	private List<String> params;
	/**
	 * 操作人
	 * */
	private String tel;
	private String name;
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getWay() {
		return way;
	}
	public void setWay(Integer way) {
		this.way = way;
	}
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "UserSendRequest [cpId=" + cpId + ", type=" + type + ", way=" + way + ", params=" + params + ", tel="
				+ tel + ", name=" + name + "]";
	}
	
	
	
}
