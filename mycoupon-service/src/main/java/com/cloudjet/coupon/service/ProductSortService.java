package com.cloudjet.coupon.service;
import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.model.ProductModel;
import com.cloudjet.coupon.model.ShopSortModel;
import com.cloudjet.coupon.model.SortDetailModel;
import com.cloudjet.coupon.model.SortProductModel;
import com.cloudjet.coupon.request.ProductListParamsModel;
import com.cloudjet.coupon.request.ProductSortListModel;
import com.cloudjet.coupon.res.model.ProductDetailModel;
import com.cloudjet.coupon.util.PageResult;

public interface ProductSortService {

	/**
	 * 创建商品分类
	 * @param sortProductModel
	 * @throws CouponException
	 */
	void createSort(SortProductModel sortProductModel) throws CouponException;
	
	
	/**
	 * 查询商铺分类
	 * @param shopCode
	 * @return
	 */
	ShopSortModel querySort(String shopCode) throws CouponException;


	/**
	 * 查询商铺分类列表
	 * @param productSortListModel
	 * @return
	 * @throws CouponException
	 */
	PageResult<SortDetailModel> querySortList(ProductSortListModel productSortListModel) throws CouponException;
	
	/**
	 * 查看分类详情
	 * @param sortId
	 * @return
	 * @throws CouponException
	 */
	SortDetailModel querySortDetail(String sortId) throws CouponException;
	
	/**
	 * 编辑分类
	 * @param sortDetailModel
	 * @throws CouponException
	 */
	void editSort(SortDetailModel sortDetailModel) throws CouponException;
	
	/**
	 * 删除分类
	 * @param String sortId
	 */
	void deleteSort(String sortId) throws CouponException;
		
	/**
	 * 店铺活动商品添加
	 * @param productModel
	 * @throws CouponException
	 */
	void manageProduct(ProductModel productModel) throws CouponException;
	
	/**
	 * 店铺活动商品删除
	 * @param productId shopCode
	 * @throws CouponException
	 */
	void deleteProduct(String shopCode, String productId) throws CouponException;
		
	/**
	 * 店铺活动商品查看
	 * @param productId
	 * @return
	 * @throws CouponException
	 */
	PageResult<ProductDetailModel> productList(ProductListParamsModel productListParamsModel) throws CouponException;
	

}
