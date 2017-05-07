package com.cloudjet.coupon.mapper;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.InfoCodeEntity;
import com.cloudjet.coupon.entity.dto.InfoCodeMsgEntity;

public interface InfoCodeMapper {

	int add(InfoCodeEntity infoCodeEntity);
	
	InfoCodeEntity queryDistinct(@Param("platCode") String platCode,@Param("infoCode") String infoCode);
	
	
	/**
	 * 查询绑定优惠券下面出未使用code码
	 * @params String cpId
	 * @return InfoCodeEntity 
	 * */
	InfoCodeEntity queryCodeId(String cpId);
	
	InfoCodeMsgEntity queryCodeMsg(String cpId);
	
	/**
	 * code与用户券包绑定
	 * */
	int bindBagId(String userBagId, String id);
	
	int delete(@Param("codePlanId") String codePlanId);
}
