package com.cloudjet.coupon.model;

import java.io.Serializable;

public class CoupnConvertDetailModel implements Serializable{

	/**
	 * 兑换码详情model
	 */
	private static final long serialVersionUID = 1L;

	public enum ConvertCodeStatus{
		
		/**
		 * 初始化
		 * */
		init,
		/**
		 * 已绑定
		 * */
		bind
		
	}
	
	/**
	 * 兑换码绑定状态
	 */
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CoupnConvertDetailModel [status=" + status + "]";
	}
	
	
}
