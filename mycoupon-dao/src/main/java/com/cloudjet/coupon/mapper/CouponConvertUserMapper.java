package com.cloudjet.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.CouponConvertUserEntity;

public interface CouponConvertUserMapper {

	int save(CouponConvertUserEntity couponConvertUserEntity);
	
	List<CouponConvertUserEntity> queryCodeUser(@Param("convertId")String convertId);
}
