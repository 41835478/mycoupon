package com.cloudjet.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface JphUserMapper {
	
	List<String> selectTels(@Param("levels")List<String> levels);
	
	int selectCount(@Param("cpId")String cpId);
	
	String queryJphLevel(@Param("userTel") String userTel);
		
}
