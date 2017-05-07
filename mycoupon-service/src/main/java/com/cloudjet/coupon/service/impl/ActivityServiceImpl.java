package com.cloudjet.coupon.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudjet.coupon.entity.CouponActivityEntity;
import com.cloudjet.coupon.entity.CouponWordEntity;
import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.mapper.CouponActivityMapper;
import com.cloudjet.coupon.mapper.CouponWordMapper;
import com.cloudjet.coupon.model.ActivityModel;
import com.cloudjet.coupon.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService{
	
	@Autowired
	private CouponWordMapper couponWordMapper;
	
	@Autowired
	private CouponActivityMapper couponActivityMapper;
	
	@Override
	public ActivityModel checkWord(String word,String activityId) throws CouponException {
		CouponWordEntity cwe = couponWordMapper.queryDetail(word, activityId);
		if (cwe == null) {
			return null;
		} else {
			CouponActivityEntity cae = couponActivityMapper.query(activityId);
			ActivityModel am = new ActivityModel();
			BeanUtils.copyProperties(cae, am);
			return am;
		}
	}
	
}
