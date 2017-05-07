package com.cloudjet.coupon.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.CouponConvertPlanEntity;
import com.cloudjet.coupon.entity.dto.CouponConvertListEntity;

public interface CouponConvertPlanMapper {
	
	int save(CouponConvertPlanEntity CouponConvertPlanEntity);
	
	List<CouponConvertListEntity> queryCodeList(Map<String, Object> params);
	
	void updateInfoId(@Param("convertPlanId") String convertPlanId,@Param("infoId") String infoId,@Param("type") Integer type);
	
	int queryCodeListCount();
	
	CouponConvertPlanEntity query(@Param("convertPlanId") String convertPlanId);
	
	void delete(@Param("convertPlanId") String convertPlanId);
	
}
