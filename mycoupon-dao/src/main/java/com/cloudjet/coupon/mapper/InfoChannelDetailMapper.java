package com.cloudjet.coupon.mapper;


import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.InfoChannelDetailEntity;

public interface InfoChannelDetailMapper {

	int add(InfoChannelDetailEntity infoChannelDetailEntity);
	
	void delete(@Param("cpId")String cpId);
}
