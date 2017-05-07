package com.cloudjet.coupon.model;

import java.io.Serializable;

public class UserBagLogModel implements Serializable{

	
	/**
	 * 券包券日志领取使用 0,已领取  1,已使用  2,已删除,3已使用（未付款）
	 * */
	public enum UserBagLogType{
		
		get,
		cost,
		del,
		unpay
		
	}
	
	private static final long serialVersionUID = 1L;
	
	
}
