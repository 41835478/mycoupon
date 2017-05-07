package com.cloudjet.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.CouponPackageInfoEntity;
import com.cloudjet.coupon.entity.dto.CouponInfoDetailEntity;

public interface CouponPackageInfoMapper {

	int add(CouponPackageInfoEntity couponPackageInfoEntity);
	
	int delete(@Param("packageId") String packageId);
	
	List<CouponInfoDetailEntity> queryInfoDetail(@Param("packageId") String packageId);
}
