package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;

public class InfoScopeModel implements Serializable{

	/**
	 * 商品优惠券编号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 券范围 0,全场券；1，分类卷；2，商品券；3，供应商券
	 * */
	public enum ScopeType{
		
		all, 
		sort,
		goods,
		suppliers
		
	}
	
	/**
	 * 券范围 0,全场券；1，分类卷；2，商品券；3，供应商券
	 * */
	private Integer type;
	
	/**
	 * 为供应商id,分类代号,商品id
	 * */
	private List<String> nos;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<String> getNos() {
		return nos;
	}

	public void setNos(List<String> nos) {
		this.nos = nos;
	}

	@Override
	public String toString() {
		return "InfoScopeModel [type=" + type + ", nos=" + nos + "]";
	}

	
	
}
