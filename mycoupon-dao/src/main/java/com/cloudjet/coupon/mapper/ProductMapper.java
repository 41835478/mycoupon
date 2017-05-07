package com.cloudjet.coupon.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cloudjet.coupon.entity.ProductEntity;

public interface ProductMapper {
	
	int save(ProductEntity productEntity);
	
	void delete(@Param("shopCode") String shopCode, @Param("productId") String productId); 

	ProductEntity query(@Param("shopCode") String shopCode, @Param("productId") String productId); 
	
	List<ProductEntity> queryShopProduct(Map<String,Object> params);
	
	int queryCount(Map<String,Object> params);
	
}
