package com.cloudjet.coupon.mapper;

import com.cloudjet.coupon.entity.CouponWordEntity;

public interface CouponWordMapper {
	
	CouponWordEntity queryDetail(String word,String activityId);
	
}
