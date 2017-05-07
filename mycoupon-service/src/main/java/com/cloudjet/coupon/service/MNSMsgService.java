package com.cloudjet.coupon.service;

import com.cloudjet.coupon.entity.MNSMsgEntity;
import com.cloudjet.coupon.exception.CouponException;

public interface MNSMsgService {
	
	 void save(MNSMsgEntity mnsMsg) throws CouponException;
	
}
