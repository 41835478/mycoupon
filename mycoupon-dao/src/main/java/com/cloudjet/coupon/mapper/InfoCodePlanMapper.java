package com.cloudjet.coupon.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.InfoCodePlanEntity;
import com.cloudjet.coupon.entity.dto.CodeListEntity;

public interface InfoCodePlanMapper {

	int add(InfoCodePlanEntity infoCodePlanEntity);
	
	void update(InfoCodePlanEntity infoCodePlanEntity);
	
	int delete(@Param("codePlanId") String codePlanId);
	
	void unbind(@Param("codePlanId") String codePlanId);
	
	InfoCodePlanEntity query(@Param("codePlanId") String codePlanId);
	
	void updateMsgStatus(@Param("codePlanId") String codePlanId,@Param("isMsg") String isMsg);
	
	List<CodeListEntity> queryCodeList(Map<String, Object> params);
	
	int queryCodeListCount(Map<String, Object> params);
	
	
}
