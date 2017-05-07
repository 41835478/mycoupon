package com.cloudjet.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.InfoUserSourceDetailEntity;

public interface InfoUserSourceDetailMapper {

	int add(InfoUserSourceDetailEntity infoUserSourceDetailEntity);
	
	int batchSave(@Param("infoUserSourceDetailEntitys")List<InfoUserSourceDetailEntity> infoUserSourceDetailEntitys);
	
	List<String> queryUserByTel(@Param("userTel") String userTel,@Param("cpId") String cpId);
}
