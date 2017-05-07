package com.cloudjet.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.SendRecordEntity;
import com.cloudjet.coupon.entity.dto.SendRecordDeatilEntity;

public interface SendRecordMapper {
	
	int add(SendRecordEntity sendCountEntity);
	
	List<SendRecordDeatilEntity> sendRecordDetail(@Param("cpId")String cpId);
}
