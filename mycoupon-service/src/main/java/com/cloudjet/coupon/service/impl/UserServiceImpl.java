package com.cloudjet.coupon.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudjet.coupon.bean.User;
import com.cloudjet.coupon.entity.UserEntity;
import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.mapper.UserMapper;
import com.cloudjet.coupon.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;

	@Transactional
	public int save(User user) throws CouponException {
		
		logger.info("123");
		logger.debug("456");
		logger.error("789");
		
		UserEntity ue = new UserEntity();
		ue.setPassword(user.getPassword());
		ue.setUserName(user.getUsername());
		
		int foo = userMapper.add(ue);
		return foo;
	}

}
