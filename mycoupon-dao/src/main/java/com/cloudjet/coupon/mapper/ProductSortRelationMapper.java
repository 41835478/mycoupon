package com.cloudjet.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.cloudjet.coupon.entity.ProductSortRelationEntity;

public interface ProductSortRelationMapper {

	int save(ProductSortRelationEntity productSortRelationEntity);

	List<ProductSortRelationEntity> query(@Param("sortId")String sortId);
	
	int delete(@Param("sortId")String sortId);
}
