package com.cloudjet.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.InfoUserSourceLevelEntity;

public interface InfoUserSourceLevelMapper {

	int add(InfoUserSourceLevelEntity infoUserSourceLevelEntity);
	
	int batchSave(@Param("infoUserSourceLevelEntitys")List<InfoUserSourceLevelEntity> infoUserSourceLevelEntitys);
	
	List<String> queryLevels(@Param("cpId") String cpId);
}
