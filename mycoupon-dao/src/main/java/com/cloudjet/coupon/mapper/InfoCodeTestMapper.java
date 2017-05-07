package com.cloudjet.coupon.mapper;

import com.cloudjet.coupon.entity.InfoCodeTestEntity;

public interface InfoCodeTestMapper {

	int save(InfoCodeTestEntity infoCodeTestEntity);
	
	int queryCount(String codePlanId);
}
