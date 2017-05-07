package com.cloudjet.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.InfoChannelEntity;

public interface InfoChannelMapper {

	int add(InfoChannelEntity infoChannelEntity);
	
	void delete(@Param("cpId") String cpId);
	
	List<InfoChannelEntity> queryList(@Param("cpId") String cpId);
}
