package com.cloudjet.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.CouponShopInfoEntity;

public interface CouponShopInfoMapper {

	int add(CouponShopInfoEntity couponShopInfoEntity);
	
	void delete(@Param("cpId") String cpId);
	
	List<String> selectPlatCodes(@Param("cpId") String cpId);
	
	List<CouponShopInfoEntity> select(@Param("cpId") String cpId);
}
