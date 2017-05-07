package com.cloudjet.coupon.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.ProductSortEntity;
import com.cloudjet.coupon.entity.dto.ProductDetailEntity;

public interface ProductSortMapper {

	int save(ProductSortEntity productSortEntity);
	
	List<ProductSortEntity> query(@Param("shopCode")String shopCode);
	
	List<ProductSortEntity> queryList(Map<String,Object> params);
	
	int queryCount(Map<String,Object> params);
	
	int delete(@Param("sortId")String sortId);
	
	List<ProductDetailEntity> queryProducts(@Param("sortId")String sortId);
	
	int update(@Param("sortId")String sortId,@Param("name")String name);
	
	ProductSortEntity querySortDetail(@Param("sortId")String sortId);
	
	//查询商品id的分类
	ProductDetailEntity queryByProductId(@Param("shopCode")String shopCode, @Param("productId")String productId);
}
