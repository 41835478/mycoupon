package com.cloudjet.coupon.mapper;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.CouponConvertCodeEntity;
import com.cloudjet.coupon.entity.CouponPackageEntity;
import com.cloudjet.coupon.entity.dto.ConvertCodeDetailEntity;
import com.cloudjet.coupon.entity.dto.CouponInfoDetailEntity;


public interface CouponConvertCodeMapper{

	int save(CouponConvertCodeEntity couponConvertCodeEntity);
	
	CouponInfoDetailEntity queryCoupon(@Param("code") String code);
	
	ConvertCodeDetailEntity query(@Param("code") String code);
	
	void updateCode(@Param("code") String code); 
	
	void delete(@Param("convertPlanId") String convertPlanId);
	
	CouponPackageEntity queryPackage(@Param("code") String code);
}
