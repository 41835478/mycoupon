package com.cloudjet.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.LogoEntity;

public interface LogoMapper {

	int save(LogoEntity logoEntity);
	
	int delete(@Param("infoId")String infoId);
	
	List<LogoEntity> select(@Param("infoId")String infoId);
}
