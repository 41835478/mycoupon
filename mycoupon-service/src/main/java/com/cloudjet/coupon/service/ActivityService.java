package com.cloudjet.coupon.service;

import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.model.ActivityModel;

public interface ActivityService {
	
	public ActivityModel checkWord(String word,String activityId) throws CouponException;
	
}
