package com.cloudjet.coupon.service;

import com.cloudjet.coupon.exception.CouponException;

public interface ProduceCouponKeyService {
	
	/**
	 * 创建一批兑换码
	 */
	void createConvertCode(int count) throws CouponException;
	
}
