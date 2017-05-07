package com.cloudjet.coupon.mapper;

import com.cloudjet.coupon.entity.CouponActivityEntity;

public interface CouponActivityMapper {
	
	CouponActivityEntity query(String activityId);
	
}
