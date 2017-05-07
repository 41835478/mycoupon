package com.cloudjet.coupon.mapper;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.InfoConditionEntity;

public interface InfoConditionMapper {
	int add(InfoConditionEntity infoConditionEntity);
	
	void delete(@Param("cpId") String cpId);

}
