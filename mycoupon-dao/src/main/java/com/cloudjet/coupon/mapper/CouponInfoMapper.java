package com.cloudjet.coupon.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.CouponInfoEntity;
import com.cloudjet.coupon.entity.InfoChannelDetailEntity;
import com.cloudjet.coupon.entity.InfoChannelEntity;
import com.cloudjet.coupon.entity.dto.CodeMsgDetailEntity;
import com.cloudjet.coupon.entity.dto.CouponInfoCheckedListEntity;
import com.cloudjet.coupon.entity.dto.CouponInfoDetailEntity;
import com.cloudjet.coupon.entity.dto.ProductDetailEntity;

public interface CouponInfoMapper {
	
	int add(CouponInfoEntity couponInfoEntity);
	
	void delete(@Param("cpId") String cpId);
	
	void updateStatus(@Param("cpId") String cpId,@Param("status")int status);
		
	CouponInfoEntity query(@Param("cpId") String cpId);
	
	CodeMsgDetailEntity queryByCodePlanId(@Param("codePlanId") String codePlanId);
	
	List<CouponInfoDetailEntity> queryList(Map<String, Object> params);
	
	int editStock(@Param("cpId") String cpId);

	CouponInfoDetailEntity queryDetail(@Param("cpId") String cpId);
	
	List<InfoChannelEntity> queryChannels(@Param("cpId") String cpId);
	
	List<InfoChannelDetailEntity> queryChannelDetails(@Param("cpId") String cpId);
		
	void updateAll(Map<String,Object> params);
	
	int selectCount(Map<String,Object> params);
	
	List<CouponInfoCheckedListEntity> queryCheckedList(Map<String, Object> params);
	
	void updateStock(@Param("cpId") String cpId,@Param("stock")int stock);
	
	int updateUserSourceType(@Param("cpId") String cpId,@Param("userSourceType")int userSourceType);
	
	void updateCodeStatus(@Param("cpId") String cpId,@Param("status")int status);
	
	List<ProductDetailEntity> queryProducts(String cpId);
	
	
}
