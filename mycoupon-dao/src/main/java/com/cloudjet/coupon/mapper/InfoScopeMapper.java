package com.cloudjet.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.InfoScopeEntity;

public interface InfoScopeMapper {
	int add(InfoScopeEntity infoScopeEntity);
	
	void delete(@Param("cpId") String cpId);
	
	List<InfoScopeEntity> queryAll(@Param("cpId") String cpId);
	
	List<String> query(@Param("cpId") String cpId);
}
