package com.cloudjet.coupon.service;

import com.cloudjet.coupon.bean.User;
import com.cloudjet.coupon.exception.CouponException;

public interface UserService 
{
    int save(User user) throws CouponException;
}
