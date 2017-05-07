package com.cloudjet.coupon.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.UserBagEntity;
import com.cloudjet.coupon.entity.dto.CouponInfoDetailEntity;
import com.cloudjet.coupon.entity.dto.UserBagFindEntity;
import com.cloudjet.coupon.entity.dto.UserBagResEntity;

public interface UserBagMapper {
	int add(UserBagEntity userBagEntity);
	
	int batchSave(@Param("userBagEntitys")List<UserBagEntity> userBagEntitys);
	
	List<UserBagEntity> selectByCouponId(@Param("cpId")String cpId);
	
	int countSingel(@Param("cpId") String cpId,@Param("userTel") String userTel);
	
	int findTotal(Map<String,Object> params);
	
	List<UserBagFindEntity> queryBags(Map<String,Object> params);
	
	void update(@Param("id") String id,@Param("status") int status);
	
	UserBagFindEntity queryBagDetail(@Param("userBagId") String userBagId);

	List<UserBagEntity> findUserBags(@Param("orderNo") String orderNo);
	
	List<UserBagResEntity> findUserBagRes(Map<String,Object> params);
	
	CouponInfoDetailEntity queryCoupon(@Param("userBagId") String userBagId,@Param("tel") String tel);
	
	UserBagEntity getNoUsed(@Param("cpId") String cpId, @Param("tel") String tel);
	
}
