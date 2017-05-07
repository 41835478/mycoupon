package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.Map;

public class ShopSortModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 商铺分类。key-分类id,value 分类名字
	 */
	private Map<String,String> idAndNames;

	public Map<String, String> getIdAndNames() {
		return idAndNames;
	}

	public void setIdAndNames(Map<String, String> idAndNames) {
		this.idAndNames = idAndNames;
	}

	@Override
	public String toString() {
		return "ShopSortModel [idAndNames=" + idAndNames + "]";
	}

	
}
