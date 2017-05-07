package com.cloudjet.coupon.mapper;

import com.cloudjet.coupon.entity.InfoUserSourceEntity;

public interface InfoUserSourceMapper {

	int add(InfoUserSourceEntity infoUserSourceEntity);
	
	InfoUserSourceEntity query(String infoId);
}
