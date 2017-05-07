package com.cloudjet.coupon.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.cloudjet.coupon.entity.ProductEntity;
import com.cloudjet.coupon.entity.ProductLogEntity;
import com.cloudjet.coupon.entity.ProductSortEntity;
import com.cloudjet.coupon.entity.ProductSortLogEntity;
import com.cloudjet.coupon.entity.ProductSortRelationEntity;
import com.cloudjet.coupon.entity.dto.ProductDetailEntity;
import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.mapper.ProductLogMapper;
import com.cloudjet.coupon.mapper.ProductMapper;
import com.cloudjet.coupon.mapper.ProductSortLogMapper;
import com.cloudjet.coupon.mapper.ProductSortMapper;
import com.cloudjet.coupon.mapper.ProductSortRelationMapper;
import com.cloudjet.coupon.model.ProductLogModel.ProductStatusEnum;
import com.cloudjet.coupon.model.ProductModel;
import com.cloudjet.coupon.model.ProductSortLogModel.SortLogTypeEnum;
import com.cloudjet.coupon.model.ShopSortModel;
import com.cloudjet.coupon.model.SortDetailModel;
import com.cloudjet.coupon.model.SortProductModel;
import com.cloudjet.coupon.request.ProductListParamsModel;
import com.cloudjet.coupon.request.ProductSortListModel;
import com.cloudjet.coupon.request.ProductSortListModel.PaginationEnum;
import com.cloudjet.coupon.res.model.ProductDetailModel;
import com.cloudjet.coupon.service.ProductSortService;
import com.cloudjet.coupon.util.PageResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class ProductSortServiceImpl implements ProductSortService{

	private static final Logger logger = LoggerFactory.getLogger(ProductSortServiceImpl.class);
	
	@Autowired
	private  ProductSortMapper productSortMapper;
	
	@Autowired
	private  ProductSortRelationMapper productSortRelationMapper;
	
	@Autowired
	private  ProductSortLogMapper productSortLogMapper;
	
	@Autowired
	private  ProductMapper productMapper;
	
	@Autowired
	private  ProductLogMapper productLogMapper;
	
	@Override
	@Transactional
	public void createSort(SortProductModel sortProductModel) throws CouponException {

		Map<String,List<String>> productSortIdMaps = sortProductModel.getProductSortIdMaps();
		Set<String> keySet = productSortIdMaps.keySet();
		Iterator<String> iterator = keySet.iterator();	
		
		//遍历map
		while(iterator.hasNext()) {
			
			String sortName = iterator.next();
			List<String> productIds = productSortIdMaps.get(sortName);
			
			//记录分类
			ProductSortEntity productSortEntity = new ProductSortEntity();
			productSortEntity.setName(sortName);
			productSortEntity.setShopCode(sortProductModel.getShopCode());
			productSortMapper.save(productSortEntity);
			
			productIds.forEach(productId ->{
				
				//记录商品
				ProductSortRelationEntity psre = new ProductSortRelationEntity();
				psre.setProductId(productId);
				psre.setSortId(productSortEntity.getId());
				
				try {
					productSortRelationMapper.save(psre);
				} catch (Exception e) {
		 			logger.error("历史分类中已经包含该商品={}");
		 			throw new CouponException("S50");
				}
							
				//分类日志
				ProductSortLogEntity psle = new ProductSortLogEntity(); 
				psle.setShopCode(sortProductModel.getShopCode());
				psle.setSortId(productSortEntity.getId());
				psle.setName(sortName);
				psle.setProductId(productId);
				psle.setType(SortLogTypeEnum.init.ordinal());
				productSortLogMapper.save(psle);
			});
		}
	}

	@Override
	public ShopSortModel querySort(String shopCode) throws CouponException {
			
		//查询出商铺号下所有品类
		List<ProductSortEntity> pses = productSortMapper.query(shopCode);
		Map<String,String> idAndNames = Maps.newHashMap();
		ShopSortModel  shopSortModel = new ShopSortModel();
		
		pses.forEach(pse ->{
			idAndNames.put(pse.getId(),pse.getName());
		});
		
		shopSortModel.setIdAndNames(idAndNames);
		return shopSortModel;
	}

	@Override
	public PageResult<SortDetailModel> querySortList(ProductSortListModel productSortListModel) throws CouponException {
		
		PageResult<SortDetailModel> page = new PageResult<SortDetailModel>();
		List<SortDetailModel> sortDetailModels = Lists.newArrayList();	
		
		Map<String, Object> params = Maps.newHashMap();
		params.put("shopCode", productSortListModel.getShopCode());
		params.put("name", productSortListModel.getName());
		
		//是否分页
		if (productSortListModel.getPagination() == PaginationEnum.no.ordinal()) {
			params.put("start",null);
			params.put("limit",null);	
		} else {
			params.put("start", (productSortListModel.getPageNo()-1)*productSortListModel.getPageSize());
			params.put("limit", productSortListModel.getPageSize());
		}
		
		//查询出商铺号下所有品类
		List<ProductSortEntity> pses = productSortMapper.queryList(params);
		
		pses.forEach(pse ->{
			
			SortDetailModel sortDetailModel = new SortDetailModel();
			sortDetailModel.setName(pse.getName());
			sortDetailModel.setSortId(pse.getId());
			sortDetailModel.setShopCode(productSortListModel.getShopCode());
			
			//查询品类对应的所有商品
			List<String> productIds = Lists.newArrayList();
			List<ProductSortRelationEntity> psres= productSortRelationMapper.query(pse.getId());
			psres.forEach(psre ->{
				productIds.add(psre.getProductId());
			});
			
			sortDetailModel.setProductIds(productIds);
			sortDetailModels.add(sortDetailModel);
		});

		page.setData(sortDetailModels);
		
		//计算分页
		int total = productSortMapper.queryCount(params);
		page.setTotalResults(total);
		
		if (productSortListModel.getPagination() == PaginationEnum.no.ordinal()) {
			page.setHasNext(false);
		} else {
			int totalPage = (total + productSortListModel.getPageSize() - 1) / productSortListModel.getPageSize();
			page.setHasNext(productSortListModel.getPageNo()+1 <=  totalPage);
		}
		
		return page;
	}

	@Override
	@Transactional
	public void editSort(SortDetailModel sortDetailModel) throws CouponException {
		
		//1、更新分类名字
		productSortMapper.update(sortDetailModel.getSortId(), sortDetailModel.getName());
		
		//2、更新商品。先删除，再重新绑定。
		productSortRelationMapper.delete(sortDetailModel.getSortId());
		List<String> productIds = sortDetailModel.getProductIds();
		
		productIds.forEach(productId ->{
			//记录商品
			ProductSortRelationEntity psre = new ProductSortRelationEntity();
			psre.setProductId(productId);
			psre.setSortId(sortDetailModel.getSortId());
			try {
				productSortRelationMapper.save(psre);
			} catch (Exception e) {
	 			logger.error("历史分类中已经包含该商品={}");
	 			throw new CouponException("S50");
			}
			
			//分类日志
			ProductSortLogEntity psle = new ProductSortLogEntity(); 
			psle.setShopCode(sortDetailModel.getShopCode());
			psle.setSortId(sortDetailModel.getSortId());
			psle.setName(sortDetailModel.getName());
			psle.setProductId(productId);
			psle.setType(SortLogTypeEnum.edit.ordinal());
			productSortLogMapper.save(psle);
		});
	}

	@Override
	@Transactional
	public void deleteSort(String sortId) throws CouponException {
		
		//记录分类日志
		List<ProductDetailEntity> pdes = productSortMapper.queryProducts(sortId);
		pdes.forEach(pde ->{
			ProductSortLogEntity psle = new ProductSortLogEntity();
			psle.setName(pde.getName());
			psle.setProductId(pde.getProductId());
			psle.setShopCode(pde.getShopCode());
			psle.setSortId(pde.getSortId());
			psle.setType(SortLogTypeEnum.delete.ordinal());
			productSortLogMapper.save(psle);
		});
		
		//删除
		productSortRelationMapper.delete(sortId);
		productSortMapper.delete(sortId);
		
	}

	@Override
	public SortDetailModel querySortDetail(String sortId) throws CouponException {
		
		ProductSortEntity pse = productSortMapper.querySortDetail(sortId);
		
		SortDetailModel sortDetailModel = new SortDetailModel();
		sortDetailModel.setName(pse.getName());
		sortDetailModel.setSortId(pse.getId());
		sortDetailModel.setShopCode(pse.getShopCode());
		
		//查询品类对应的所有商品
		List<String> productIds = Lists.newArrayList();
		List<ProductSortRelationEntity> psres= productSortRelationMapper.query(pse.getId());
		psres.forEach(psre ->{
			productIds.add(psre.getProductId());
		});
		
		sortDetailModel.setProductIds(productIds);
		
		return sortDetailModel;
	}

	@Override
	@Transactional
	public void manageProduct(ProductModel productModel) throws CouponException {
		
		Map<String, String> products = productModel.getProducts();
		Set<String> keySet = products.keySet();
		Iterator<String> iterator = keySet.iterator();	
		
		//遍历map
		while(iterator.hasNext()) {
			String productId = iterator.next();
			String productName = products.get(productId);
			
			//1、插入商品
			ProductEntity productEntity = new ProductEntity();
			productEntity.setShopCode(productModel.getShopCode());
			productEntity.setProductId(productId);
			productEntity.setProductName(productName);
			productEntity.setShopName(productModel.getShopName());
			
			productMapper.save(productEntity);
			
			//2、log记录
			ProductLogEntity productLogEntity = new ProductLogEntity();
			productLogEntity.setShopCode(productModel.getShopCode());
			productLogEntity.setProductId(productId);
			productLogEntity.setProductName(productName);
			productLogEntity.setShopName(productModel.getShopName());
			productLogEntity.setType(ProductStatusEnum.init.ordinal());
			
			productLogMapper.save(productLogEntity);
		}

	}

	@Override
	@Transactional
	public void deleteProduct(String shopCode, String productId) throws CouponException {
	
		ProductEntity productEntity = productMapper.query(shopCode, productId);
		
		//log 记录
		ProductLogEntity productLogEntity = new ProductLogEntity();
		productLogEntity.setShopCode(productEntity.getShopCode());
		productLogEntity.setProductId(productId);
		productLogEntity.setProductName(productEntity.getProductName());
		productLogEntity.setShopName(productEntity.getShopName());
		productLogEntity.setType(ProductStatusEnum.delete.ordinal());
		
		productLogMapper.save(productLogEntity);
		
		//删除
		productMapper.delete(shopCode, productId);
	}

	@Override
	public PageResult<ProductDetailModel> productList(ProductListParamsModel productListParamsModel) throws CouponException {

		PageResult<ProductDetailModel> page = new PageResult<ProductDetailModel>();
		Map<String,Object> params = Maps.newHashMap();
		params.put("shopCode", productListParamsModel.getShopCode());
		params.put("productName", productListParamsModel.getProductName());
		params.put("shopName", productListParamsModel.getShopName());
		
		//计算总数目
		int total = productMapper.queryCount(params);
		page.setTotalResults(total);
		
		if (productListParamsModel.getPagination() == PaginationEnum.no.ordinal()) { //不分页
			params.put("start", null);
			params.put("limit", null);
			page.setHasNext(false);
		} else { //分页
			params.put("start", (productListParamsModel.getPageNo()-1)*productListParamsModel.getPageSize());
			params.put("limit", productListParamsModel.getPageSize());
			
			//计算页数
			int totalPage = (total + productListParamsModel.getPageSize() - 1) / productListParamsModel.getPageSize();
			page.setHasNext(productListParamsModel.getPageNo()+1 <=  totalPage);
		}
		
		List<ProductDetailModel> productDetailModels = Lists.newArrayList();
		List<ProductEntity> productEntitys = productMapper.queryShopProduct(params);//查询商品
		
		if (!CollectionUtils.isEmpty(productEntitys)) {
			
			productEntitys.forEach(productEntity ->{
				
				ProductDetailModel productDetailModel = new ProductDetailModel();
				productDetailModel.setShopCode(productEntity.getShopCode());
				productDetailModel.setShopName(productEntity.getShopName());
				productDetailModel.setProductId(productEntity.getProductId());
				productDetailModel.setProductName(productEntity.getProductName());
				
				productDetailModels.add(productDetailModel);
			});
			
		}
		page.setData(productDetailModels);
		return page;
	}


}
