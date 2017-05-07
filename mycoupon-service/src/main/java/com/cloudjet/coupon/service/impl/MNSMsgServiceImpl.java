package com.cloudjet.coupon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudjet.coupon.entity.MNSMsgEntity;
import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.mapper.MNSMsgMapper;
import com.cloudjet.coupon.service.MNSMsgService;

@Service
public class MNSMsgServiceImpl implements MNSMsgService{
	
	@Autowired
	private MNSMsgMapper mnsMsgMapper;

	@Override
	public void save(MNSMsgEntity mnsMsg) throws CouponException {
		mnsMsgMapper.save(mnsMsg);
	}
	
	
	
}
