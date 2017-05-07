package com.cloudjet.coupon.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.CouponPackageEntity;

public interface CouponPackageMapper {

	int add(CouponPackageEntity couponPackageEntity);
	
	List<CouponPackageEntity> queryPackageList(Map<String, Object> params);
	
	int updateStatus(@Param("status") Integer status,@Param("packageId") String packageId);
	

	void updatePackage(Map<String, Object> params);
	
	int delete(@Param("packageId") String packageId);
	
	CouponPackageEntity query(@Param("packageId") String packageId);
	
	int queryPackageListCount(Map<String, Object> params);
}
