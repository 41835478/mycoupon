package com.cloudjet.coupon.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import static ch.lambdaj.Lambda.*;

import com.cloudjet.coupon.entity.ProductEntity;
import com.cloudjet.coupon.entity.UserBagLogEntity;
import com.cloudjet.coupon.entity.UserBagOrderEntity;
import com.cloudjet.coupon.entity.dto.ProductDetailEntity;
import com.cloudjet.coupon.entity.dto.UserBagFindEntity;
import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.mapper.CouponInfoMapper;
import com.cloudjet.coupon.mapper.InfoScopeMapper;
import com.cloudjet.coupon.mapper.ProductMapper;
import com.cloudjet.coupon.mapper.ProductSortMapper;
import com.cloudjet.coupon.mapper.UserBagLogMapper;
import com.cloudjet.coupon.mapper.UserBagMapper;
import com.cloudjet.coupon.mapper.UserBagOrderMapper;
import com.cloudjet.coupon.model.CouponInfoModel.CouponInfoCostType;
import com.cloudjet.coupon.model.CouponInfoModel.CouponInfoType;
import com.cloudjet.coupon.model.CouponInfoModel.CouponIsMmeanwhileEnum;
import com.cloudjet.coupon.model.CouponOrderModel;
import com.cloudjet.coupon.model.CouponOrderModel.IsHave;
import com.cloudjet.coupon.model.CouponOrderModel.UseCategoryEnum;
import com.cloudjet.coupon.model.CouponOrderModel.UsePlat;
import com.cloudjet.coupon.model.CouponOrderModel.UseProduct;
import com.cloudjet.coupon.model.CouponOrderResModel;
import com.cloudjet.coupon.model.CouponProductModel;
import com.cloudjet.coupon.model.MeanWhileMarkModel;
import com.cloudjet.coupon.model.UserBagFindModel;
import com.cloudjet.coupon.model.UserBagLogModel.UserBagLogType;
import com.cloudjet.coupon.model.UserBagModel;
import com.cloudjet.coupon.model.UserBagModel.UserBagBestUsed;
import com.cloudjet.coupon.model.UserBagModel.UserBagStatus;
import com.cloudjet.coupon.service.OrderService;
import com.cloudjet.coupon.util.ArithUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class OrderServiceImpl implements OrderService{

	public static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private  UserBagMapper userBagMapper;

	@Autowired
	private  InfoScopeMapper infoScopeMapper;

	@Autowired
	private UserBagLogMapper userBagLogMapper;

	@Autowired
	private UserBagOrderMapper userBagOrderMapper;

	@Autowired
	private ProductSortMapper productSortMapper;

	@Autowired
	private CouponInfoMapper couponInfoMapper;
	
	@Autowired
	private  ProductMapper productMapper;

	@Override
	public CouponOrderResModel place(CouponOrderModel couponOrderModel) throws CouponException {
		
		//订单总金额
		Double totalOrigin = 0.00;
		for(int i=0;i<couponOrderModel.getProducts().size();i++){
			totalOrigin = ArithUtil.add(totalOrigin, ArithUtil.mul(couponOrderModel.getProducts().get(i).getPrice(), couponOrderModel.getProducts().get(i).getCount()));
		}
		
		CouponOrderResModel couponOrderRes = this.getBestCouponNew(couponOrderModel);

		couponOrderRes.setUsePlat(couponOrderModel.getUsePlat());
		couponOrderRes.setUseProduct(couponOrderModel.getUseProduct());
		couponOrderRes.setUseCategory(couponOrderModel.getUseCategory());
		
		//1.商品券，品类券，平台劵都使用
		if(couponOrderModel.getUseProduct() == UseProduct.yes.ordinal()
				&& couponOrderModel.getUseCategory() == UseCategoryEnum.yes.ordinal()
					&& couponOrderModel.getUsePlat() == UsePlat.yes.ordinal() ){
			if(StringUtils.isNotBlank(couponOrderModel.getUserBagId())){
			
				UserBagFindEntity ubfe = userBagMapper.queryBagDetail(couponOrderModel.getUserBagId());
				if(ubfe == null){
		 			logger.error("优惠券自选失败={}");
		 			throw new CouponException("S12");
				}
				UserBagModel userBagSelfModel = new UserBagModel();
			    BeanUtils.copyProperties(ubfe, userBagSelfModel);
				couponOrderRes.setUserBagSelfModel(userBagSelfModel);
				
			}else{
				Double productTotal = sum(couponOrderRes.getUserBagProducts(),on(UserBagModel.class).getPar());
				Double categoryTotal = sum(couponOrderRes.getUserBagCategorys(),on(UserBagModel.class).getPar());
				Double platTotal = 0.00;
				if(couponOrderRes.getUserBagPlats() != null){
					UserBagModel ubm = selectMax(couponOrderRes.getUserBagPlats(),on(UserBagModel.class).getPar());
					platTotal = ubm.getPar();
				}
				couponOrderRes.setCouponProductBestPrice(productTotal);
				couponOrderRes.setCouponCategoryBestPrice(categoryTotal);
				couponOrderRes.setCouponPlatBestPrice(platTotal);
				
				Double  couponTotal  = ArithUtil.add(productTotal, ArithUtil.add(categoryTotal, platTotal));
				
				if(couponTotal > totalOrigin){
					logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
		 			throw new CouponException("S111");
				}
			}
		//2.商品券，品类券，不使用平台券
		}else if(couponOrderModel.getUseProduct() == UseProduct.yes.ordinal()
					&& couponOrderModel.getUseCategory() == UseCategoryEnum.yes.ordinal()
					 && couponOrderModel.getUsePlat() == UsePlat.no.ordinal() ){
		
				Double productTotal = sum(couponOrderRes.getUserBagProducts(),on(UserBagModel.class).getPar());
				Double categoryTotal = sum(couponOrderRes.getUserBagCategorys(),on(UserBagModel.class).getPar());
				
				couponOrderRes.setCouponProductBestPrice(productTotal);
				couponOrderRes.setCouponCategoryBestPrice(categoryTotal);
				
				Double couponTotal  = ArithUtil.add(productTotal, categoryTotal);
				
				if(couponTotal > totalOrigin){
					logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
		 			throw new CouponException("S111");
				}
		//3.商品券，不使用品类券，不使用平台券
		}else if(couponOrderModel.getUseProduct() == UseProduct.yes.ordinal()
					&& couponOrderModel.getUseCategory() == UseCategoryEnum.no.ordinal()
						&& couponOrderModel.getUsePlat() == UsePlat.no.ordinal() ){
			Double productTotal = sum(couponOrderRes.getUserBagProducts(),on(UserBagModel.class).getPar());
			couponOrderRes.setCouponProductBestPrice(productTotal);
			if(productTotal > totalOrigin){
				logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
	 			throw new CouponException("S111");
			}
			
		//4.商品券，不使用品类券，平台券	
		}else if(couponOrderModel.getUseProduct() == UseProduct.yes.ordinal()
				&& couponOrderModel.getUseCategory() == UseCategoryEnum.no.ordinal()
				&& couponOrderModel.getUsePlat() == UsePlat.yes.ordinal() ){
			
			if(StringUtils.isNotBlank(couponOrderModel.getUserBagId())){
				
				UserBagFindEntity ubfe = userBagMapper.queryBagDetail(couponOrderModel.getUserBagId());
				if(ubfe == null){
		 			logger.error("优惠券自选失败={}");
		 			throw new CouponException("S12");
				}
				UserBagModel userBagSelfModel = new UserBagModel();
			    BeanUtils.copyProperties(ubfe, userBagSelfModel);
				couponOrderRes.setUserBagSelfModel(userBagSelfModel);
				
			}else{
				Double productTotal = sum(couponOrderRes.getUserBagProducts(),on(UserBagModel.class).getPar());
				Double platTotal = 0.00;
				if(couponOrderRes.getUserBagPlats() != null){
					UserBagModel ubm = selectMax(couponOrderRes.getUserBagPlats(),on(UserBagModel.class).getPar());
					platTotal = ubm.getPar();
				}
				couponOrderRes.setCouponProductBestPrice(productTotal);
				couponOrderRes.setCouponPlatBestPrice(platTotal);
				
				Double  couponTotal  = ArithUtil.add(productTotal, platTotal);
				
				if(couponTotal > totalOrigin){
					logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
		 			throw new CouponException("S111");
				}
			}
	
		//5.不使用商品券，品类券，平台券	
		}else if(couponOrderModel.getUseProduct() == UseProduct.no.ordinal()
					&& couponOrderModel.getUseCategory() == UseCategoryEnum.yes.ordinal()
					 && couponOrderModel.getUsePlat() == UsePlat.yes.ordinal() ){
			
			if(StringUtils.isNotBlank(couponOrderModel.getUserBagId())){
				
				UserBagFindEntity ubfe = userBagMapper.queryBagDetail(couponOrderModel.getUserBagId());
				if(ubfe == null){
		 			logger.error("优惠券自选失败={}");
		 			throw new CouponException("S12");
				}
				UserBagModel userBagSelfModel = new UserBagModel();
			    BeanUtils.copyProperties(ubfe, userBagSelfModel);
				couponOrderRes.setUserBagSelfModel(userBagSelfModel);
				
			}else{
				Double categoryTotal = sum(couponOrderRes.getUserBagCategorys(),on(UserBagModel.class).getPar());
				Double platTotal = 0.00;
				if(couponOrderRes.getUserBagPlats() != null){
					UserBagModel ubm = selectMax(couponOrderRes.getUserBagPlats(),on(UserBagModel.class).getPar());
					platTotal = ubm.getPar();
				}
				couponOrderRes.setCouponCategoryBestPrice(categoryTotal);
				couponOrderRes.setCouponPlatBestPrice(platTotal);
				
				Double  couponTotal  = ArithUtil.add( categoryTotal, platTotal);
				
				if(couponTotal > totalOrigin){
					logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
		 			throw new CouponException("S111");
				}
			}
			
		//6.不使用商品券，品类券，不使用平台券	
		}else if(couponOrderModel.getUseProduct() == UseProduct.no.ordinal()
					&& couponOrderModel.getUseCategory() == UseCategoryEnum.yes.ordinal()
						&& couponOrderModel.getUsePlat() == UsePlat.no.ordinal() ){
			Double categoryTotal = sum(couponOrderRes.getUserBagCategorys(),on(UserBagModel.class).getPar());
			
			couponOrderRes.setCouponCategoryBestPrice(categoryTotal);
			
			if(categoryTotal > totalOrigin){
				logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
	 			throw new CouponException("S111");
			}
		//7.不使用商品券，不使用品类券，平台券	
		}else if(couponOrderModel.getUseProduct() == UseProduct.no.ordinal()
				&& couponOrderModel.getUseCategory() == UseCategoryEnum.no.ordinal()
					&& couponOrderModel.getUsePlat() == UsePlat.yes.ordinal() ){
			
			if(StringUtils.isNotBlank(couponOrderModel.getUserBagId())){
				
				UserBagFindEntity ubfe = userBagMapper.queryBagDetail(couponOrderModel.getUserBagId());
				if(ubfe == null){
		 			logger.error("优惠券自选失败={}");
		 			throw new CouponException("S12");
				}
				UserBagModel userBagSelfModel = new UserBagModel();
			    BeanUtils.copyProperties(ubfe, userBagSelfModel);
				couponOrderRes.setUserBagSelfModel(userBagSelfModel);
				
			}else{
				Double platTotal = 0.00;
				if(couponOrderRes.getUserBagPlats() != null){
					UserBagModel ubm = selectMax(couponOrderRes.getUserBagPlats(),on(UserBagModel.class).getPar());
					platTotal = ubm.getPar();
				}
				couponOrderRes.setCouponPlatBestPrice(platTotal);
				if(platTotal > totalOrigin){
					logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
		 			throw new CouponException("S111");
				}
			}
		}
		return couponOrderRes;

	}

	//得出优惠券的最优系统判断的最优组合
	@SuppressWarnings("static-access")
	public CouponOrderResModel getBestCouponNew(CouponOrderModel couponOrder){
		
		CouponOrderResModel couponOrderRes = new CouponOrderResModel();
		
		//筛选出不能参加优惠的商品
		CouponOrderModel couponOrderModel = new CouponOrderModel();
		List<CouponProductModel> products = couponOrder.getProducts();
		
		List<CouponProductModel> pts = Lists.newArrayList();
		products.forEach(product->{
			ProductEntity pe = productMapper.query(couponOrder.getPlatCode(), product.getSpuCode());
			if(pe == null){
				pts.add(product);
			}
		});
		if(CollectionUtils.isEmpty(pts)){//购买商品都不能参加优惠
			
			couponOrderRes.setIsHaveProduct(IsHave.no.ordinal());
			couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
			couponOrderRes.setIsHaveCategory(IsHave.no.ordinal());
			
			return couponOrderRes;
		}
		BeanUtils.copyProperties(couponOrder, couponOrderModel);
		couponOrderModel.setProducts(pts);
		
		
		//查询出用户卡券包优惠券
		Map<String,Object> params = Maps.newHashMap();
		params.put("tel", couponOrderModel.getUserTel());
		params.put("platCode", couponOrderModel.getPlatCode());
		//此处要判断线上券
		params.put("costType", CouponInfoCostType.online.ordinal());
		List<UserBagFindEntity> ubfs = userBagMapper.queryBags(params);
		List<CouponProductModel> cpms = couponOrderModel.getProducts();
		//计算出原始订单总价格
		//2.计算出购买商品的总金额(订单的总金额)
		Double totalOrigin = 0.00;
		for(int i=0;i<cpms.size();i++){
			totalOrigin = ArithUtil.add(totalOrigin, ArithUtil.mul(cpms.get(i).getPrice(),cpms.get(i).getCount()));
		}
		couponOrderRes.setTotalOrigin(totalOrigin);


		if(!CollectionUtils.isEmpty(ubfs)){

			List<String> sortIdPss = Lists.newArrayList();
			List<CouponProductModel> couponProducts = couponOrderModel.getProducts();
			couponProducts.forEach(couponProduct ->{
				String productId = couponProduct.getSpuCode();
				ProductDetailEntity pde = productSortMapper.queryByProductId(couponOrderModel.getPlatCode(),
						productId);
				if(pde != null){
					couponProduct.setSort(pde.getSortId());
					sortIdPss.add(pde.getSortId());
				}
			});

			//区分出单品券  全场券 品类券
			List<UserBagFindEntity> ubfsProducts = Lists.newArrayList();
			List<UserBagFindEntity> ubfsPlats = Lists.newArrayList();
			List<UserBagFindEntity> ubfsCategorys1 = Lists.newArrayList();

			ubfs.forEach(ubfe ->{
				if(ubfe.getCouponType() == CouponInfoType.goods.ordinal()){//商品
					List<String> nos = infoScopeMapper.query(ubfe.getCpId());
					ubfe.setProductIds(nos);
					ubfsProducts.add(ubfe);
				}else if(ubfe.getCouponType() == CouponInfoType.platform.ordinal()){//平台
					ubfsPlats.add(ubfe);
				}else{
					ubfsCategorys1.add(ubfe);
				}
			});

			if(!CollectionUtils.isEmpty(ubfsProducts)){//商品券
			
				//1.挨个查询出订单的商品
				//2循环按照顺序，拉取一个可使用优惠券的副本集合
				List<UserBagFindModel> ubfms = Lists.newArrayList();
				ubfsProducts.forEach(ubfsProduct ->{
					UserBagFindModel ubfm = new UserBagFindModel();
					BeanUtils.copyProperties(ubfsProduct, ubfm);
					ubfms.add(ubfm);
				});
				//3.循环出每个商品对应的优惠券集合
				for(int i = 0;i<cpms.size();i++){
					List<UserBagFindModel> ubfmProducts = Lists.newArrayList();
					for(int j = 0;j<ubfms.size();j++){
						if(ubfms.get(j).getProductIds().contains(cpms.get(i).getSpuCode())){
							//判断商品优惠券类型
							//1.满减
							if(StringUtils.equals("reduce", ubfms.get(j).getType())){
								if(cpms.get(i).getPrice() >= ubfms.get(j).getStartFee()){//商品价格大于券startFee
									ubfms.get(j).setProductId(cpms.get(i).getSpuCode());
									ubfms.get(j).setSortId(cpms.get(i).getSort());
									ubfmProducts.add(ubfms.get(j));

								}
							}else{//2.无条件
								ubfms.get(j).setProductId(cpms.get(i).getSpuCode());
								ubfms.get(j).setSortId(cpms.get(i).getSort());
								ubfmProducts.add(ubfms.get(j));
							}

						}
					}
					//计算出商品优惠券最优使用
					if(!CollectionUtils.isEmpty(ubfmProducts)){

						UserBagFindModel userBagFindModel = selectMax(ubfmProducts, on(UserBagFindModel.class).getPar());

						if(ubfms.contains(userBagFindModel)){
							ubfms.remove(userBagFindModel);
							if(cpms.get(i).getPrice() < userBagFindModel.getPar()){//商品金额小于优惠券金额
								logger.error("优惠券金额大于商品金额={}",cpms.get(i).getSpuCode()+":"+couponOrderModel.getUserTel());
					 			throw new CouponException("S49");
							}
							cpms.get(i).setUserBagFindModel(userBagFindModel);
						}
					}
				}
				//1.计算出商品优惠券优惠的总金额
				Double goodTotalPrice = 0.00;
				List<UserBagModel> ubms = Lists.newArrayList();//最终商品优惠券使用列表


				for(int i=0;i<cpms.size();i++){
					if(cpms.get(i).getUserBagFindModel() == null){
						continue;
					}else{
						UserBagModel ubm = new UserBagModel();
						BeanUtils.copyProperties(cpms.get(i).getUserBagFindModel(), ubm);
						ubms.add(ubm);
						goodTotalPrice = ArithUtil.add(goodTotalPrice, cpms.get(i).getUserBagFindModel().getPar());


					}
				}
				if(CollectionUtils.isEmpty(ubms)){
					couponOrderRes.setIsHaveProduct(IsHave.no.ordinal());
				}else{
					couponOrderRes.setIsHaveProduct(IsHave.yes.ordinal());
					couponOrderRes.setCouponProductBestPrice(goodTotalPrice);
					couponOrderRes.setUserBagProducts(ubms);
				}
				couponOrderRes.setCouponProducts(cpms);
			}else{
				couponOrderRes.setIsHaveProduct(IsHave.no.ordinal());
			}


			//品类券
			if(CollectionUtils.isEmpty(ubfsCategorys1)){
				couponOrderRes.setIsHaveCategory(IsHave.no.ordinal());
				couponOrderRes.setUserBagCategorys(null);
			}else{
				List<UserBagFindEntity> ubfsCategorys = this.mySortRepeat(ubfsCategorys1);
				//筛选出同品类优惠券？？？  多张同品类优惠券拿出做优的选择？？？
				List<String> sortIds = Lists.newArrayList();
				ubfsCategorys.forEach(ubfsCategory ->{
					List<String> sortId = infoScopeMapper.query(ubfsCategory.getCpId());
					sortIds.add(sortId.get(0)); //分类券只能绑定一个分类
					ubfsCategory.setProductIds(sortId);
				});


				if(CollectionUtils.isEmpty(sortIdPss)){//当前购买商品没有分类，没有可用的分类券
					couponOrderRes.setIsHaveCategory(IsHave.no.ordinal());
					couponOrderRes.setUserBagCategorys(null);
				}else{
					List<String> sortIdPs = Lists.newArrayList();//购买商品的分类
					Set<String> hs = new HashSet<String>(sortIdPss);
					Iterator<String> it = hs.iterator();
					while(it.hasNext()){
						sortIdPs.add(it.next());
					}
					sortIdPs.retainAll(sortIds);
					if(CollectionUtils.isEmpty(sortIdPs)){//没有可用的分类券
						couponOrderRes.setIsHaveCategory(IsHave.no.ordinal());
						couponOrderRes.setUserBagCategorys(null);
					}else{//
						//取出可以用的分类券
						List<UserBagFindEntity> ubfsCategoryChanges = Lists.newArrayList();
						for(int i = 0;i<ubfsCategorys.size();i++){
							for(int j = 0;j<sortIdPs.size();j++){
								if(StringUtils.equals(sortIdPs.get(j), ubfsCategorys.get(i).getProductIds().get(0))){
									ubfsCategoryChanges.add(ubfsCategorys.get(i));
								}
							}
						}

						List<UserBagModel> ubmCategorys = Lists.newArrayList();

						ubfsCategoryChanges.forEach(ubfsCategory ->{
							UserBagModel ubm = new UserBagModel();
							//查询出分类券包含什么商品
							List<ProductDetailEntity> productDetails = couponInfoMapper.queryProducts(ubfsCategory.getCpId());
							List<String> productIds = Lists.newArrayList();
							productDetails.forEach(productDetail ->{
								productIds.add(productDetail.getProductId());
							});
							String tempSortId = ubfsCategory.getProductIds().get(0);
							ubfsCategory.setProductIds(productIds);
							BeanUtils.copyProperties(ubfsCategory, ubm);
							ubm.setSortId(tempSortId);
							ubmCategorys.add(ubm);
						});

						//进一步筛选分类券  //满减
						CouponProductModel tmpCpm = null;
						Map<String,CouponProductModel> mapp = Maps.newHashMap();
						for(CouponProductModel cpm : couponOrderModel.getProducts()){
							if(cpm.getSort() == null){
								continue;
							}
							tmpCpm = mapp.get(cpm.getSort());
							if(tmpCpm !=null){
								tmpCpm.setPrice(ArithUtil.add(ArithUtil.mul(cpm.getCount(), cpm.getPrice()), ArithUtil.mul(tmpCpm.getCount(), tmpCpm.getPrice())));
							}else{
								mapp.put(cpm.getSort(), cpm);
							}
						}
						List<UserBagModel> deleteCategoryReduces = Lists.newArrayList();
						for(UserBagModel ubm : ubmCategorys){
							if(StringUtils.equals(ubm.getType(), "reduce")){
								Double ubmProductPrice = mapp.get(ubm.getSortId()).getPrice();
								if(ubmProductPrice < ubm.getStartFee()){
									deleteCategoryReduces.add(ubm);
								}
							}else{
								continue;
							}
						}
						if(!CollectionUtils.isEmpty(deleteCategoryReduces)){
							ubmCategorys.removeAll(deleteCategoryReduces);
						}
						if(CollectionUtils.isEmpty(ubmCategorys)){
						}else{
							//计算出购买商品的分类
							List<UserBagModel> ubms = couponOrderRes.getUserBagProducts();
							if(ubms != null){
								//商品券相同品类 既有商品券，又有品类券,比较par大小决定使用品类还是商品
								UserBagModel tmpUbm = null;
								Map<String,UserBagModel> map = Maps.newHashMap();
								List<UserBagModel> ubmsCopy = Lists.newArrayList();
								for(UserBagModel ubm : ubms){
									
									UserBagModel ubmBak = new UserBagModel();
									BeanUtils.copyProperties(ubm, ubmBak);
									ubmsCopy.add(ubmBak);
								}
								for(UserBagModel ubm : ubmsCopy){
									if(ubm.getSortId() == null){
										continue;
									}
									tmpUbm = map.get(ubm.getSortId());
									if(tmpUbm !=null){
										tmpUbm.setPar(ArithUtil.add(tmpUbm.getPar(), ubm.getPar()));
									}else{
										map.put(ubm.getSortId(), ubm);
									}
								}
	
								List<UserBagModel> deleteCategorys = Lists.newArrayList();
								List<UserBagModel> deleteProducts = Lists.newArrayList();
								
								if(map.size() > 0){
									for(UserBagModel ubm : ubmCategorys){
										if(map.get(ubm.getSortId()) == null){
											continue;
										}else{
											Double ubmProductPar = map.get(ubm.getSortId()).getPar();
											if(ubm.getMeantime() == 1 && map.get(ubm.getSortId()).getMeantime() == 1){//都能同时使用
												continue;
											}else{
												if(ubmProductPar >= ubm.getPar()){
													deleteCategorys.add(ubm);//不使用品类券
												}else{//不使用当前商品券
													for(UserBagModel ubmProduct : ubms){
														if(StringUtils.equals(ubmProduct.getSortId(), ubm.getSortId())){
															deleteProducts.add(ubmProduct);
														}
													}
												}
											}
										}
										
										
									}
								}
								if(!CollectionUtils.isEmpty(deleteCategorys)){
									ubmCategorys.removeAll(deleteCategorys);
								}
								if(!CollectionUtils.isEmpty(deleteProducts)){
									ubms.removeAll(deleteProducts);
								}
						  }	
							if(CollectionUtils.isEmpty(ubms)){//商品券为空
								couponOrderRes.setIsHaveProduct(IsHave.no.ordinal());
								couponOrderRes.setUserBagProducts(null);
							}else{
								couponOrderRes.setIsHaveProduct(IsHave.yes.ordinal());
								couponOrderRes.setUserBagProducts(ubms);
							}
							if(CollectionUtils.isEmpty(ubmCategorys)){//品类券为空
								couponOrderRes.setIsHaveCategory(IsHave.no.ordinal());
								couponOrderRes.setUserBagCategorys(null);
							}else{
								
								//筛选出同品类最优的优惠券
								UserBagModel tmpUbmCategory = null;
								Map<String,UserBagModel> mapCategory = Maps.newHashMap();
								for(UserBagModel ub : ubmCategorys){
									tmpUbmCategory = mapCategory.get(ub.getSortId());
									if(tmpUbmCategory !=null){
										if(ub.getPar() > tmpUbmCategory.getPar()){
											mapCategory.put(ub.getSortId(), ub);
										}
									}else{
										mapCategory.put(ub.getSortId(), ub);
									}
								}
								List<UserBagModel> resultCategorys = Lists.newArrayList();
								for(String key : mapCategory.keySet()){
									resultCategorys.add(mapCategory.get(key));
								}
								
								couponOrderRes.setIsHaveCategory(IsHave.yes.ordinal());
								couponOrderRes.setUserBagCategorys(resultCategorys);
							}

						}

					}

				}

			}
			//平台券
			if(!CollectionUtils.isEmpty(ubfsPlats)){
				
				//去除重复的平台优惠券，平台券只能使用一张
				if(couponOrderRes.getUserBagProducts() != null && couponOrderRes.getUserBagCategorys() !=null){
					//totalOrigin  订单的总金额
					//计算出可用的平台券
					//.满减券，全场 可用的
					List<UserBagFindEntity> ubfsPlatsUseds = Lists.newArrayList();
					for(int i=0;i<ubfsPlats.size();i++){
						if(StringUtils.equals(ubfsPlats.get(i).getType(),"reduce")){//满减
							if(totalOrigin >= ubfsPlats.get(i).getStartFee()){
								ubfsPlatsUseds.add(ubfsPlats.get(i));
							}
						}else if(StringUtils.equals(ubfsPlats.get(i).getType(),"zero")){
							ubfsPlatsUseds.add(ubfsPlats.get(i));
						}
					}
					UserBagFindEntity userBagFindPlatMax = selectMax(ubfsPlatsUseds, on(UserBagFindEntity.class).getPar());
					//选出不能与平台券共用的商品券最大值
					List<UserBagModel> userBagProductsTemps = Lists.newArrayList();
					for(UserBagModel ubm : couponOrderRes.getUserBagProducts()){
						if(ubm.getMeanwhile() == 0 || userBagFindPlatMax.getMeantime() ==0){//不能与平台券同时使用
							userBagProductsTemps.add(ubm);
						}
					}
					Double productSumPar = sum(userBagProductsTemps,on(UserBagModel.class).getPar());
					if(userBagFindPlatMax.getPar() < productSumPar){
						couponOrderRes.setUserBagPlats(null);
						couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
					}else{
						couponOrderRes.getUserBagProducts().removeAll(userBagProductsTemps);

						List<UserBagModel> userBagPlats = Lists.newArrayList();
						for(int i=0;i<ubfsPlatsUseds.size();i++){
							UserBagModel ubm = new UserBagModel();
							BeanUtils.copyProperties(ubfsPlatsUseds.get(i), ubm);
							if(StringUtils.equals(ubfsPlatsUseds.get(i).getUserBagId(), userBagFindPlatMax.getUserBagId())){
								ubm.setBestUsed(UserBagBestUsed.best.ordinal());
							}
							userBagPlats.add(ubm);
						}
						if(couponOrderRes.getUserBagProducts() == null){
							couponOrderRes.setIsHaveProduct(IsHave.no.ordinal());
						}
						couponOrderRes.setUserBagPlats(userBagPlats);
						couponOrderRes.setIsHavePlat(IsHave.yes.ordinal());
					}
					//选出不能与平台券共用的品类券最大值
					List<UserBagModel> userBagCategorysTemps = Lists.newArrayList();
					for(UserBagModel ubm : couponOrderRes.getUserBagCategorys()){
						if(ubm.getMeanwhile() == 0 || userBagFindPlatMax.getMeanwhile() ==0){//不能与平台券同时使用
							userBagCategorysTemps.add(ubm);
						}
					}
					Double categorySumPar = sum(userBagCategorysTemps,on(UserBagModel.class).getPar());
					if(userBagFindPlatMax.getPar() < categorySumPar){
						couponOrderRes.setUserBagPlats(null);
						couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
					}else{
						couponOrderRes.getUserBagCategorys().removeAll(userBagCategorysTemps);
						
						List<UserBagModel> userBagPlats = Lists.newArrayList();
						for(int i=0;i<ubfsPlatsUseds.size();i++){
							UserBagModel ubm = new UserBagModel();
							BeanUtils.copyProperties(ubfsPlatsUseds.get(i), ubm);
							if(StringUtils.equals(ubfsPlatsUseds.get(i).getUserBagId(), userBagFindPlatMax.getUserBagId())){
								ubm.setBestUsed(UserBagBestUsed.best.ordinal());
							}
							userBagPlats.add(ubm);
						}
						if(couponOrderRes.getUserBagCategorys() == null){
							couponOrderRes.setIsHaveCategory(IsHave.no.ordinal());
						}
						couponOrderRes.setUserBagPlats(userBagPlats);
						couponOrderRes.setIsHavePlat(IsHave.yes.ordinal());
					}

				}else if(couponOrderRes.getUserBagProducts() != null && couponOrderRes.getUserBagCategorys() == null){

					//totalOrigin  订单的总金额
					//计算出可用的平台券
					//.满减券，全场 可用的
					List<UserBagFindEntity> ubfsPlatsUseds = Lists.newArrayList();
					for(int i=0;i<ubfsPlats.size();i++){
						if(StringUtils.equals(ubfsPlats.get(i).getType(),"reduce")){//满减
							if(totalOrigin >= ubfsPlats.get(i).getStartFee()){
								ubfsPlatsUseds.add(ubfsPlats.get(i));
							}
						}else if(StringUtils.equals(ubfsPlats.get(i).getType(),"zero")){
							ubfsPlatsUseds.add(ubfsPlats.get(i));
						}
					}
					UserBagFindEntity userBagFindPlatMax = selectMax(ubfsPlatsUseds, on(UserBagFindEntity.class).getPar());
					//选出不能与平台券共用的商品券最大值
					List<UserBagModel> userBagProductsTemps = Lists.newArrayList();
					for(UserBagModel ubm : couponOrderRes.getUserBagProducts()){
						if(ubm.getMeanwhile() == 0){//不能与平台券同时使用
							userBagProductsTemps.add(ubm);
						}
					}
					Double productSumPar = sum(userBagProductsTemps,on(UserBagModel.class).getPar());
					if(userBagFindPlatMax.getPar() < productSumPar){
						couponOrderRes.setUserBagPlats(null);
						couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
					}else{
						couponOrderRes.getUserBagProducts().removeAll(userBagProductsTemps);
						List<UserBagModel> userBagPlats = Lists.newArrayList();
						for(int i=0;i<ubfsPlatsUseds.size();i++){
							UserBagModel ubm = new UserBagModel();
							BeanUtils.copyProperties(ubfsPlatsUseds.get(i), ubm);
							if(StringUtils.equals(ubfsPlatsUseds.get(i).getUserBagId(), userBagFindPlatMax.getUserBagId())){
								ubm.setBestUsed(UserBagBestUsed.best.ordinal());
							}
							userBagPlats.add(ubm);
						}
						if(CollectionUtils.isEmpty(couponOrderRes.getUserBagProducts())){
							couponOrderRes.setIsHaveProduct(IsHave.no.ordinal());
						}
						couponOrderRes.setUserBagPlats(userBagPlats);
						couponOrderRes.setIsHavePlat(IsHave.yes.ordinal());
					}


				}else if(couponOrderRes.getUserBagProducts() == null && couponOrderRes.getUserBagCategorys() != null){

					//totalOrigin  订单的总金额
					//计算出可用的平台券
					//.满减券，全场 可用的
					List<UserBagFindEntity> ubfsPlatsUseds = Lists.newArrayList();
					for(int i=0;i<ubfsPlats.size();i++){
						if(StringUtils.equals(ubfsPlats.get(i).getType(),"reduce")){//满减
							if(totalOrigin >= ubfsPlats.get(i).getStartFee()){
								ubfsPlatsUseds.add(ubfsPlats.get(i));
							}
						}else if(StringUtils.equals(ubfsPlats.get(i).getType(),"zero")){
							ubfsPlatsUseds.add(ubfsPlats.get(i));
						}
					}
					UserBagFindEntity userBagFindPlatMax = selectMax(ubfsPlatsUseds, on(UserBagFindEntity.class).getPar());
					
					//选出不能与平台券共用的品类券最大值
					List<UserBagModel> userBagCategorysTemps = Lists.newArrayList();
					for(UserBagModel ubm : couponOrderRes.getUserBagCategorys()){
						if(ubm.getMeanwhile() == 0){//不能与平台券同时使用
							userBagCategorysTemps.add(ubm);
						}
					}
					Double categorySumPar = sum(userBagCategorysTemps,on(UserBagModel.class).getPar());
					
					
					/*
					if(userBagFindPlatMax.getPar() < categorySumPar){
						
						Double difference = ArithUtil.sub(categorySumPar, userBagFindPlatMax.getPar());
						couponOrderRes.getUserBagCategorys().removeAll(userBagCategorysTemps);
						
						
						
						couponOrderRes.setUserBagPlats(null);
						couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
					}else{
						couponOrderRes.getUserBagCategorys().removeAll(userBagCategorysTemps);
						List<UserBagModel> userBagPlats = Lists.newArrayList();
						for(int i=0;i<ubfsPlatsUseds.size();i++){
							UserBagModel ubm = new UserBagModel();
							BeanUtils.copyProperties(ubfsPlatsUseds.get(i), ubm);
							if(StringUtils.equals(ubfsPlatsUseds.get(i).getUserBagId(), userBagFindPlatMax.getUserBagId())){
								ubm.setBestUsed(UserBagBestUsed.best.ordinal());
							}
							userBagPlats.add(ubm);
						}
						System.out.println(couponOrderRes.getUserBagCategorys().size());
						if(couponOrderRes.getUserBagCategorys() == null){
							couponOrderRes.setIsHaveCategory(IsHave.no.ordinal());
						}
						couponOrderRes.setUserBagPlats(userBagPlats);
						couponOrderRes.setIsHavePlat(IsHave.yes.ordinal());
					}*/
					
					
					if(userBagFindPlatMax.getPar() < categorySumPar){
						couponOrderRes.setUserBagPlats(null);
						couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
					}else{
						couponOrderRes.getUserBagCategorys().removeAll(userBagCategorysTemps);
						List<UserBagModel> userBagPlats = Lists.newArrayList();
						for(int i=0;i<ubfsPlatsUseds.size();i++){
							UserBagModel ubm = new UserBagModel();
							BeanUtils.copyProperties(ubfsPlatsUseds.get(i), ubm);
							if(StringUtils.equals(ubfsPlatsUseds.get(i).getUserBagId(), userBagFindPlatMax.getUserBagId())){
								ubm.setBestUsed(UserBagBestUsed.best.ordinal());
							}
							userBagPlats.add(ubm);
						}
						System.out.println(couponOrderRes.getUserBagCategorys().size());
						if(CollectionUtils.isEmpty(couponOrderRes.getUserBagCategorys())){
							couponOrderRes.setIsHaveCategory(IsHave.no.ordinal());
						}
						couponOrderRes.setUserBagPlats(userBagPlats);
						couponOrderRes.setIsHavePlat(IsHave.yes.ordinal());
					}

				}else{
					List<UserBagFindEntity> ubfsPlatsUseds = Lists.newArrayList();
					for(int i=0;i<ubfsPlats.size();i++){
						if(StringUtils.equals(ubfsPlats.get(i).getType(),"reduce")){//满减
							if(totalOrigin >= ubfsPlats.get(i).getStartFee()){
								ubfsPlatsUseds.add(ubfsPlats.get(i));
							}
						}else if(StringUtils.equals(ubfsPlats.get(i).getType(),"zero")){
							ubfsPlatsUseds.add(ubfsPlats.get(i));
						}
					}
					if(CollectionUtils.isEmpty(ubfsPlatsUseds)){
						couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
					}else{
						UserBagFindEntity userBagFindPlatMax = selectMax(ubfsPlatsUseds, on(UserBagFindEntity.class).getPar());
						//3.整个优惠后订单的金额
						couponOrderRes.setCouponPrice(userBagFindPlatMax.getPar());
						couponOrderRes.setCouponPlatBestPrice(userBagFindPlatMax.getPar());
						couponOrderRes.setCouponBestPrice(userBagFindPlatMax.getPar());
						List<UserBagModel> userBags = Lists.newArrayList();
						//4.使用的平台券
						for(int i=0;i<ubfsPlatsUseds.size();i++){
							UserBagModel ubm = new UserBagModel();
							BeanUtils.copyProperties(ubfsPlatsUseds.get(i), ubm);
							if(StringUtils.equals(ubfsPlatsUseds.get(i).getUserBagId(), userBagFindPlatMax.getUserBagId())){
								ubm.setBestUsed(UserBagBestUsed.best.ordinal());
							}
							userBags.add(ubm);
						}
						couponOrderRes.setUserBagPlats(userBags);
						couponOrderRes.setIsHavePlat(IsHave.yes.ordinal());
					}
				}

			}else{//返回有商品券的优惠信息,品类券的优惠信息
				couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
				if(!CollectionUtils.isEmpty(couponOrderRes.getUserBagProducts())){
					Double originTotal = 0.00;
					for(UserBagModel ubm:couponOrderRes.getUserBagProducts()){
						ArithUtil.add(originTotal,ubm.getPar());
					}
					couponOrderRes.setCouponProductBestPrice(originTotal);
					couponOrderRes.setIsHaveProduct(IsHave.yes.ordinal());
				}
				if(!CollectionUtils.isEmpty(couponOrderRes.getUserBagCategorys())){
					Double originTotal = 0.00;
					for(UserBagModel ubm:couponOrderRes.getUserBagCategorys()){
						ArithUtil.add(originTotal,ubm.getPar());
					}
					couponOrderRes.setCouponCategoryBestPrice(originTotal);
					couponOrderRes.setIsHaveCategory(IsHave.yes.ordinal());
				}
			}
		}else{//没有可用的优惠券
			couponOrderRes.setIsHaveProduct(IsHave.no.ordinal());
			couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
			couponOrderRes.setIsHaveCategory(IsHave.no.ordinal());
		}
		return couponOrderRes;
	}


	//得出优惠券的最优系统判断的最优组合
	public CouponOrderResModel getBestCoupon(CouponOrderModel couponOrderModel){

		List<MeanWhileMarkModel> markList = Lists.newArrayList();//不能与平台券同时使用的商品券

		CouponOrderResModel couponOrderRes = new CouponOrderResModel();

		//查询出用户卡券包优惠券
		Map<String,Object> params = Maps.newHashMap();
		params.put("tel", couponOrderModel.getUserTel());
		params.put("platCode", couponOrderModel.getPlatCode());
		//此处要判断线上券
		params.put("costType", CouponInfoCostType.online.ordinal());
		List<UserBagFindEntity> ubfs = userBagMapper.queryBags(params);
		List<CouponProductModel> cpms = couponOrderModel.getProducts();
		//计算出原始订单总价格
		//2.计算出购买商品的总金额(订单的总金额)
		Double totalOrigin = 0.00;
		for(int i=0;i<cpms.size();i++){
			totalOrigin = ArithUtil.add(totalOrigin, ArithUtil.mul(cpms.get(i).getPrice(),cpms.get(i).getCount()));
		}
		couponOrderRes.setTotalOrigin(totalOrigin);
		if(!CollectionUtils.isEmpty(ubfs)){

			//区分出单品券  全场券 品类券
			List<UserBagFindEntity> ubfsProducts = Lists.newArrayList();
			List<UserBagFindEntity> ubfsPlats = Lists.newArrayList();
			List<UserBagFindEntity> ubfscategorys = Lists.newArrayList();

			ubfs.forEach(ubfe ->{
				if(ubfe.getCouponType() == CouponInfoType.goods.ordinal()){//商品
					List<String> nos = infoScopeMapper.query(ubfe.getCpId());
					ubfe.setProductIds(nos);
					ubfsProducts.add(ubfe);
				}else if(ubfe.getCouponType() == CouponInfoType.platform.ordinal()){//平台
					ubfsPlats.add(ubfe);
				}else{
					ubfscategorys.add(ubfe);
				}
			});

			if(!CollectionUtils.isEmpty(ubfsProducts)){//商品券
				//1.挨个查询出订单的商品
				//2循环按照顺序，拉取一个可使用优惠券的副本集合
				List<UserBagFindModel> ubfms = Lists.newArrayList();
				ubfsProducts.forEach(ubfsProduct ->{
					UserBagFindModel ubfm = new UserBagFindModel();
					BeanUtils.copyProperties(ubfsProduct, ubfm);
					ubfms.add(ubfm);
				});
				//3.循环出每个商品对应的优惠券集合
				for(int i = 0;i<cpms.size();i++){
					List<UserBagFindModel> ubfmProducts = Lists.newArrayList();
					for(int j = 0;j<ubfms.size();j++){
						if(ubfms.get(j).getProductIds().contains(cpms.get(i).getSpuCode())){
							//判断商品优惠券类型
							//1.满减
							if(StringUtils.equals("reduce", ubfms.get(j).getType())){
								if(cpms.get(i).getPrice() >= ubfms.get(j).getStartFee()){//商品价格大于券startFee
									ubfmProducts.add(ubfms.get(j));
								}
							}else{//2.无条件
								ubfmProducts.add(ubfms.get(j));
							}

						}
					}
					//计算出商品优惠券最优使用
					if(!CollectionUtils.isEmpty(ubfmProducts)){

						UserBagFindModel userBagFindModel = selectMax(ubfmProducts, on(UserBagFindModel.class).getPar());

						if(ubfms.contains(userBagFindModel)){
							ubfms.remove(userBagFindModel);
							cpms.get(i).setUserBagFindModel(userBagFindModel);
						}
					}
				}
				//1.计算出商品优惠券优惠的总金额
				Double goodTotalPrice = 0.00;
				List<UserBagModel> ubms = Lists.newArrayList();//最终商品优惠券使用列表

				MeanWhileMarkModel mwm = new MeanWhileMarkModel();

				for(int i=0;i<cpms.size();i++){
					if(cpms.get(i).getUserBagFindModel() == null){
						continue;
					}else{
						UserBagModel ubm = new UserBagModel();
						BeanUtils.copyProperties(cpms.get(i).getUserBagFindModel(), ubm);
						ubms.add(ubm);
						goodTotalPrice = ArithUtil.add(goodTotalPrice, cpms.get(i).getUserBagFindModel().getPar());

						if(cpms.get(i).getUserBagFindModel().getMeanwhile() == CouponIsMmeanwhileEnum.no.ordinal()){
							mwm.setCpId(cpms.get(i).getUserBagFindModel().getCpId());
							mwm.setPar(cpms.get(i).getUserBagFindModel().getPar());
							markList.add(mwm);
						}
					}
				}
				if(CollectionUtils.isEmpty(ubms)){
					couponOrderRes.setIsHaveProduct(IsHave.no.ordinal());
				}else{
					couponOrderRes.setIsHaveProduct(IsHave.yes.ordinal());
					couponOrderRes.setCouponProductBestPrice(goodTotalPrice);
					couponOrderRes.setUserBagProducts(ubms);
				}
				couponOrderRes.setCouponProducts(cpms);
			}else{
				couponOrderRes.setIsHaveProduct(IsHave.no.ordinal());
			}

			//平台券
			if(!CollectionUtils.isEmpty(ubfsPlats)){

				Double productMaxPar = null;

				//如果有共享券存在
				if(!CollectionUtils.isEmpty(markList)){
					//选出不能与平台券共用的商品券里面最大值
					MeanWhileMarkModel mwm = selectMax(markList,on(MeanWhileMarkModel.class).getPar());
					productMaxPar = mwm.getPar();

					//计算出可用的平台券
					//.满减券，全场 可用的
					List<UserBagFindEntity> ubfsPlatsUseds = Lists.newArrayList();
					for(int i=0;i<ubfsPlats.size();i++){
						if(StringUtils.equals(ubfsPlats.get(i).getType(),"reduce")){//满减
							if(totalOrigin >= ubfsPlats.get(i).getStartFee()){
								ubfsPlatsUseds.add(ubfsPlats.get(i));
							}
						}else if(StringUtils.equals(ubfsPlats.get(i).getType(),"zero")){
							ubfsPlatsUseds.add(ubfsPlats.get(i));
						}
					}

					UserBagFindEntity userBagFindPlatMax = selectMax(ubfsPlatsUseds, on(UserBagFindEntity.class).getPar());
					if(userBagFindPlatMax.getPar() <= productMaxPar){ //返回能用商品券
						return couponOrderRes;
					}else{

						//5.1.0去除可用商品券中的不能与平台共享的优惠券
						List<UserBagModel> userBagProductsDemo = Lists.newArrayList();
						for(int i=0;i<couponOrderRes.getUserBagProducts().size();i++){
							for(int j = 0;j<markList.size();j++){
								if(StringUtils.equals(couponOrderRes.getUserBagProducts().get(i).getCpId(),markList.get(j).getCpId())){
									userBagProductsDemo.add(couponOrderRes.getUserBagProducts().get(i));
								}
							}
						}

						couponOrderRes.getUserBagProducts().removeAll(userBagProductsDemo);
						if(CollectionUtils.isEmpty(couponOrderRes.getUserBagProducts())){//
							couponOrderRes.setIsHaveProduct(IsHave.no.ordinal());
							couponOrderRes.setCouponProductBestPrice(0.00);
							couponOrderRes.setUserBagProducts(null);
						}else{
							Double goodTotalPrice = 0.00;

							for(int i=0;i<couponOrderRes.getUserBagProducts().size();i++){
								goodTotalPrice = ArithUtil.add(goodTotalPrice, couponOrderRes.getUserBagProducts().get(i).getPar());
							}
							couponOrderRes.setCouponProductBestPrice(goodTotalPrice);
						}
						//5.1.整个优惠后订单的金额
						couponOrderRes.setCouponPlatBestPrice(userBagFindPlatMax.getPar());
						couponOrderRes.setCouponBestPrice(ArithUtil.add(couponOrderRes.getCouponProductBestPrice(),
								userBagFindPlatMax.getPar()));
						couponOrderRes.setCouponPrice(couponOrderRes.getCouponBestPrice());
						List<UserBagModel> userBagPlats = Lists.newArrayList();
						//7.使用的平台券
						for(int i=0;i<ubfsPlatsUseds.size();i++){
							UserBagModel ubm = new UserBagModel();
							BeanUtils.copyProperties(ubfsPlatsUseds.get(i), ubm);
							if(StringUtils.equals(ubfsPlatsUseds.get(i).getCpId(), userBagFindPlatMax.getCpId())){
								ubm.setBestUsed(UserBagBestUsed.best.ordinal());
							}
							userBagPlats.add(ubm);
						}
						couponOrderRes.setUserBagPlats(userBagPlats);
						couponOrderRes.setIsHavePlat(IsHave.yes.ordinal());
					}


				}else{

					if(!CollectionUtils.isEmpty(couponOrderRes.getCouponProducts())){
						 //3.计算出商品优惠券优惠后的总金额
						  Double totalGoodAfter = 0.00;
						totalGoodAfter = ArithUtil.sub(totalOrigin, couponOrderRes.getCouponProductBestPrice());
						List<UserBagFindEntity> ubfsPlatsUseds = Lists.newArrayList();
						//4.满减券，全场 可用的
						for(int i=0;i<ubfsPlats.size();i++){
							if(StringUtils.equals(ubfsPlats.get(i).getType(),"reduce")){//满减
								if(totalGoodAfter >= ubfsPlats.get(i).getStartFee()){
									ubfsPlatsUseds.add(ubfsPlats.get(i));
								}
							}else if(StringUtils.equals(ubfsPlats.get(i).getType(),"zero")){
								ubfsPlatsUseds.add(ubfsPlats.get(i));
							}
						}
						if(CollectionUtils.isEmpty(ubfsPlatsUseds)){
							couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
							couponOrderRes.setCouponBestPrice(couponOrderRes.getCouponProductBestPrice());
							couponOrderRes.setCouponPrice(couponOrderRes.getCouponBestPrice());
						}else{

								UserBagFindEntity userBagFindPlatMax = selectMax(ubfsPlatsUseds, on(UserBagFindEntity.class).getPar());
								//5.1.整个优惠后订单的金额
								couponOrderRes.setCouponPlatBestPrice(userBagFindPlatMax.getPar());
								couponOrderRes.setCouponBestPrice(ArithUtil.add(couponOrderRes.getCouponProductBestPrice(),
										userBagFindPlatMax.getPar()));
								couponOrderRes.setCouponPrice(couponOrderRes.getCouponBestPrice());
								List<UserBagModel> userBagPlats = Lists.newArrayList();
								//7.使用的平台券
								for(int i=0;i<ubfsPlatsUseds.size();i++){
									UserBagModel ubm = new UserBagModel();
									BeanUtils.copyProperties(ubfsPlatsUseds.get(i), ubm);
									if(StringUtils.equals(ubfsPlatsUseds.get(i).getCpId(), userBagFindPlatMax.getCpId())){
										ubm.setBestUsed(UserBagBestUsed.best.ordinal());
									}
									userBagPlats.add(ubm);
								}
								couponOrderRes.setUserBagPlats(userBagPlats);
								couponOrderRes.setIsHavePlat(IsHave.yes.ordinal());
						}
					}else{//计算仅仅使用平台券的优惠
						//计算出订单的总价格
						//2.满减券，全场 可用的
						List<UserBagFindEntity> ubfsPlatsUseds = Lists.newArrayList();
						for(int i=0;i<ubfsPlats.size();i++){
							if(StringUtils.equals(ubfsPlats.get(i).getType(),"reduce")){//满减
								if(totalOrigin >= ubfsPlats.get(i).getStartFee()){
									ubfsPlatsUseds.add(ubfsPlats.get(i));
								}
							}else if(StringUtils.equals(ubfsPlats.get(i).getType(),"zero")){
								ubfsPlatsUseds.add(ubfsPlats.get(i));
							}
						}
						if(CollectionUtils.isEmpty(ubfsPlatsUseds)){
							couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
						}else{
							UserBagFindEntity userBagFindPlatMax = selectMax(ubfsPlatsUseds, on(UserBagFindEntity.class).getPar());
							//3.整个优惠后订单的金额
							couponOrderRes.setCouponPrice(userBagFindPlatMax.getPar());
							couponOrderRes.setCouponPlatBestPrice(userBagFindPlatMax.getPar());
							couponOrderRes.setCouponBestPrice(userBagFindPlatMax.getPar());
							List<UserBagModel> userBags = Lists.newArrayList();
							//4.使用的平台券
							for(int i=0;i<ubfsPlatsUseds.size();i++){
								UserBagModel ubm = new UserBagModel();
								BeanUtils.copyProperties(ubfsPlatsUseds.get(i), ubm);
								if(StringUtils.equals(ubfsPlatsUseds.get(i).getCpId(), userBagFindPlatMax.getCpId())){
									ubm.setBestUsed(UserBagBestUsed.best.ordinal());
								}
								userBags.add(ubm);
							}
							couponOrderRes.setUserBagPlats(userBags);
							couponOrderRes.setIsHavePlat(IsHave.yes.ordinal());
						}
					}



				}



			}else{//返回仅仅有商品券的优惠信息
				if(!CollectionUtils.isEmpty(couponOrderRes.getCouponProducts())){
					couponOrderRes.setCouponBestPrice(couponOrderRes.getCouponProductBestPrice());
					couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
				}else{
					couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
				}
			}
		}else{//没有可用的优惠券
			couponOrderRes.setIsHaveProduct(IsHave.no.ordinal());
			couponOrderRes.setIsHavePlat(IsHave.no.ordinal());
		}
		return couponOrderRes;
	}


	@Transactional
	@Override
	public CouponOrderResModel payPre(CouponOrderModel couponOrderModel) throws CouponException {

		if(StringUtils.isBlank(couponOrderModel.getOrderNo())){//主订单为空，跑异常
			logger.error("优惠券付款接口未传主订单号");
 			throw new CouponException("S13");
		}
		
		//订单总金额
		Double totalOrigin = 0.00;
		for(int i=0;i<couponOrderModel.getProducts().size();i++){
			totalOrigin = ArithUtil.add(totalOrigin, ArithUtil.mul(couponOrderModel.getProducts().get(i).getPrice(), couponOrderModel.getProducts().get(i).getCount()));
		}
		
		CouponOrderResModel couponOrderRes = this.getBestCouponNew(couponOrderModel);

		couponOrderRes.setUsePlat(couponOrderModel.getUsePlat());
		couponOrderRes.setUseProduct(couponOrderModel.getUseProduct());
		couponOrderRes.setUseCategory(couponOrderModel.getUseCategory());
		
		//1.商品券，品类券，平台劵都使用
		if(couponOrderModel.getUseProduct() == UseProduct.yes.ordinal()
				&& couponOrderModel.getUseCategory() == UseCategoryEnum.yes.ordinal()
					&& couponOrderModel.getUsePlat() == UsePlat.yes.ordinal() ){
			if(StringUtils.isNotBlank(couponOrderModel.getUserBagId())){
			
				UserBagFindEntity ubfe = userBagMapper.queryBagDetail(couponOrderModel.getUserBagId());
				if(ubfe == null){
		 			logger.error("优惠券自选失败={}");
		 			throw new CouponException("S12");
				}
				UserBagModel userBagSelfModel = new UserBagModel();
			    BeanUtils.copyProperties(ubfe, userBagSelfModel);
				couponOrderRes.setUserBagSelfModel(userBagSelfModel);
				
				Double productTotal = sum(couponOrderRes.getUserBagProducts(),on(UserBagModel.class).getPar());
				Double categoryTotal = sum(couponOrderRes.getUserBagCategorys(),on(UserBagModel.class).getPar());
				Double  couponTotal  = ArithUtil.add(productTotal, ArithUtil.add(categoryTotal, ubfe.getPar()));
				
				if(couponTotal > totalOrigin){
					logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
		 			throw new CouponException("S111");
				}
				
				this.updateProduct(couponOrderRes, couponOrderModel.getOrderNo());
				this.updateCategory(couponOrderRes, couponOrderModel.getOrderNo());
				this.updateSelfPlat(couponOrderModel.getUserBagId(), couponOrderModel.getOrderNo());
				
			}else{
				Double productTotal = sum(couponOrderRes.getUserBagProducts(),on(UserBagModel.class).getPar());
				Double categoryTotal = sum(couponOrderRes.getUserBagCategorys(),on(UserBagModel.class).getPar());
				Double platTotal = 0.00;
				if(couponOrderRes.getUserBagPlats() != null){
					UserBagModel ubm = selectMax(couponOrderRes.getUserBagPlats(),on(UserBagModel.class).getPar());
					platTotal = ubm.getPar();
				}
				
				couponOrderRes.setCouponProductBestPrice(productTotal);
				couponOrderRes.setCouponCategoryBestPrice(categoryTotal);
				couponOrderRes.setCouponPlatBestPrice(platTotal);
				
				Double  couponTotal  = ArithUtil.add(productTotal, ArithUtil.add(categoryTotal, platTotal));
				
				if(couponTotal > totalOrigin){
					logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
		 			throw new CouponException("S111");
				}
				this.updateProduct(couponOrderRes, couponOrderModel.getOrderNo());
				this.updateCategory(couponOrderRes, couponOrderModel.getOrderNo());
				this.updatePlat(couponOrderRes, couponOrderModel.getOrderNo());
			}
		//2.商品券，品类券，不使用平台券
		}else if(couponOrderModel.getUseProduct() == UseProduct.yes.ordinal()
					&& couponOrderModel.getUseCategory() == UseCategoryEnum.yes.ordinal()
					 && couponOrderModel.getUsePlat() == UsePlat.no.ordinal() ){
		
				Double productTotal = sum(couponOrderRes.getUserBagProducts(),on(UserBagModel.class).getPar());
				Double categoryTotal = sum(couponOrderRes.getUserBagCategorys(),on(UserBagModel.class).getPar());
				
				couponOrderRes.setCouponProductBestPrice(productTotal);
				couponOrderRes.setCouponCategoryBestPrice(categoryTotal);
				
				Double couponTotal  = ArithUtil.add(productTotal, categoryTotal);
				
				if(couponTotal > totalOrigin){
					logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
		 			throw new CouponException("S111");
				}
				this.updateProduct(couponOrderRes, couponOrderModel.getOrderNo());
				this.updateCategory(couponOrderRes, couponOrderModel.getOrderNo());
		//3.商品券，不使用品类券，不使用平台券
		}else if(couponOrderModel.getUseProduct() == UseProduct.yes.ordinal()
					&& couponOrderModel.getUseCategory() == UseCategoryEnum.no.ordinal()
						&& couponOrderModel.getUsePlat() == UsePlat.no.ordinal() ){
			Double productTotal = sum(couponOrderRes.getUserBagProducts(),on(UserBagModel.class).getPar());
			couponOrderRes.setCouponProductBestPrice(productTotal);
			if(productTotal > totalOrigin){
				logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
	 			throw new CouponException("S111");
			}
			
			this.updateProduct(couponOrderRes, couponOrderModel.getOrderNo());
			
			
		//4.商品券，不使用品类券，平台券	
		}else if(couponOrderModel.getUseProduct() == UseProduct.yes.ordinal()
				&& couponOrderModel.getUseCategory() == UseCategoryEnum.no.ordinal()
				&& couponOrderModel.getUsePlat() == UsePlat.yes.ordinal() ){
			
			if(StringUtils.isNotBlank(couponOrderModel.getUserBagId())){
				
				UserBagFindEntity ubfe = userBagMapper.queryBagDetail(couponOrderModel.getUserBagId());
				if(ubfe == null){
		 			logger.error("优惠券自选失败={}");
		 			throw new CouponException("S12");
				}
				UserBagModel userBagSelfModel = new UserBagModel();
			    BeanUtils.copyProperties(ubfe, userBagSelfModel);
				couponOrderRes.setUserBagSelfModel(userBagSelfModel);
				
				this.updateProduct(couponOrderRes, couponOrderModel.getOrderNo());
				this.updateSelfPlat(couponOrderModel.getUserBagId(), couponOrderModel.getOrderNo());
				
			}else{
				Double productTotal = sum(couponOrderRes.getUserBagProducts(),on(UserBagModel.class).getPar());
				Double platTotal = 0.00;
				if(couponOrderRes.getUserBagPlats() != null){
					UserBagModel ubm = selectMax(couponOrderRes.getUserBagPlats(),on(UserBagModel.class).getPar());
					platTotal = ubm.getPar();
				}
				couponOrderRes.setCouponProductBestPrice(productTotal);
				couponOrderRes.setCouponPlatBestPrice(platTotal);
				
				Double  couponTotal  = ArithUtil.add(productTotal, platTotal);
				
				if(couponTotal > totalOrigin){
					logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
		 			throw new CouponException("S111");
				}
				this.updateProduct(couponOrderRes, couponOrderModel.getOrderNo());
				this.updatePlat(couponOrderRes, couponOrderModel.getOrderNo());
			}
	
		//5.不使用商品券，品类券，平台券	
		}else if(couponOrderModel.getUseProduct() == UseProduct.no.ordinal()
					&& couponOrderModel.getUseCategory() == UseCategoryEnum.yes.ordinal()
					 && couponOrderModel.getUsePlat() == UsePlat.yes.ordinal() ){
			
			if(StringUtils.isNotBlank(couponOrderModel.getUserBagId())){
				
				UserBagFindEntity ubfe = userBagMapper.queryBagDetail(couponOrderModel.getUserBagId());
				if(ubfe == null){
		 			logger.error("优惠券自选失败={}");
		 			throw new CouponException("S12");
				}
				UserBagModel userBagSelfModel = new UserBagModel();
			    BeanUtils.copyProperties(ubfe, userBagSelfModel);
				couponOrderRes.setUserBagSelfModel(userBagSelfModel);
				
				Double categoryTotal = sum(couponOrderRes.getUserBagCategorys(),on(UserBagModel.class).getPar());
				Double  couponTotal  = ArithUtil.add(categoryTotal, ubfe.getPar());
				if(couponTotal > totalOrigin){
					logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
		 			throw new CouponException("S111");
				}
				
				this.updateCategory(couponOrderRes, couponOrderModel.getOrderNo());
				this.updateSelfPlat(couponOrderModel.getUserBagId(), couponOrderModel.getOrderNo());
			}else{
				Double categoryTotal = sum(couponOrderRes.getUserBagCategorys(),on(UserBagModel.class).getPar());
				Double platTotal = 0.00;
				if(couponOrderRes.getUserBagPlats() != null){
					UserBagModel ubm = selectMax(couponOrderRes.getUserBagPlats(),on(UserBagModel.class).getPar());
					platTotal = ubm.getPar();
				}
				
				couponOrderRes.setCouponCategoryBestPrice(categoryTotal);
				couponOrderRes.setCouponPlatBestPrice(platTotal);
				
				Double  couponTotal  = ArithUtil.add( categoryTotal, platTotal);
				
				if(couponTotal > totalOrigin){
					logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
		 			throw new CouponException("S111");
				}
				
				this.updateCategory(couponOrderRes, couponOrderModel.getOrderNo());
				this.updatePlat(couponOrderRes, couponOrderModel.getOrderNo());
			}
			
		//6.不使用商品券，品类券，不使用平台券	
		}else if(couponOrderModel.getUseProduct() == UseProduct.no.ordinal()
					&& couponOrderModel.getUseCategory() == UseCategoryEnum.yes.ordinal()
						&& couponOrderModel.getUsePlat() == UsePlat.no.ordinal() ){
			Double categoryTotal = sum(couponOrderRes.getUserBagCategorys(),on(UserBagModel.class).getPar());
			
			couponOrderRes.setCouponCategoryBestPrice(categoryTotal);
			
			if(categoryTotal > totalOrigin){
				logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
	 			throw new CouponException("S111");
			}
			this.updateCategory(couponOrderRes, couponOrderModel.getOrderNo());
		//7.不使用商品券，不使用品类券，平台券	
		}else if(couponOrderModel.getUseProduct() == UseProduct.no.ordinal()
				&& couponOrderModel.getUseCategory() == UseCategoryEnum.no.ordinal()
					&& couponOrderModel.getUsePlat() == UsePlat.yes.ordinal() ){
			
			if(StringUtils.isNotBlank(couponOrderModel.getUserBagId())){
				
				UserBagFindEntity ubfe = userBagMapper.queryBagDetail(couponOrderModel.getUserBagId());
				if(ubfe == null){
		 			logger.error("优惠券自选失败={}");
		 			throw new CouponException("S12");
				}
				UserBagModel userBagSelfModel = new UserBagModel();
			    BeanUtils.copyProperties(ubfe, userBagSelfModel);
				couponOrderRes.setUserBagSelfModel(userBagSelfModel);
				
				this.updateSelfPlat(couponOrderModel.getUserBagId(), couponOrderModel.getOrderNo());
				
			}else{
				Double platTotal = 0.00;
				if(couponOrderRes.getUserBagPlats() != null){
					UserBagModel ubm = selectMax(couponOrderRes.getUserBagPlats(),on(UserBagModel.class).getPar());
					platTotal = ubm.getPar();
				}
				couponOrderRes.setCouponPlatBestPrice(platTotal);
				if(platTotal > totalOrigin){
					logger.error("优惠券金额大于订单异常={}",couponOrderModel.getUserTel());
		 			throw new CouponException("S111");
				}
				this.updatePlat(couponOrderRes, couponOrderModel.getOrderNo());
			}
		}else{
			return couponOrderRes;
		}
		
		
	
		return couponOrderRes;

	}

	public void updateProduct(CouponOrderResModel couponOrderRes, String orderNo){
		List<UserBagModel> ubms = couponOrderRes.getUserBagProducts();
		if(ubms != null){
			ubms.forEach(ubm->{
				//修改使用商品券的状态为cost（已使用付款）、unpay(已使用未付款)
				userBagMapper.update(ubm.getUserBagId(),UserBagStatus.unpay.ordinal());

				//券日志
				UserBagLogEntity userBagLogEntity = new UserBagLogEntity();
				userBagLogEntity.setType(UserBagLogType.unpay.ordinal());
				userBagLogEntity.setUserBagId(ubm.getUserBagId());
				userBagLogMapper.add(userBagLogEntity);

				//插入订单优惠券表
				UserBagOrderEntity userBagOrderEntity = new UserBagOrderEntity();
				userBagOrderEntity.setUserBagId(ubm.getUserBagId());
				userBagOrderEntity.setOrderNo(orderNo);
				userBagOrderMapper.add(userBagOrderEntity);
			});
		}
		
	}
	
	public void updateCategory(CouponOrderResModel couponOrderRes, String orderNo){
		List<UserBagModel> ubms = couponOrderRes.getUserBagCategorys();
		if(ubms != null){
			ubms.forEach(ubm->{
				//修改使用商品券的状态为cost（已使用付款）、unpay(已使用未付款)
				userBagMapper.update(ubm.getUserBagId(),UserBagStatus.unpay.ordinal());

				//券日志
				UserBagLogEntity userBagLogEntity = new UserBagLogEntity();
				userBagLogEntity.setType(UserBagLogType.unpay.ordinal());
				userBagLogEntity.setUserBagId(ubm.getUserBagId());
				userBagLogMapper.add(userBagLogEntity);

				//插入订单优惠券表
				UserBagOrderEntity userBagOrderEntity = new UserBagOrderEntity();
				userBagOrderEntity.setUserBagId(ubm.getUserBagId());
				userBagOrderEntity.setOrderNo(orderNo);
				userBagOrderMapper.add(userBagOrderEntity);
			});
		}
		
	}
	
	public void updatePlat(CouponOrderResModel couponOrderRes, String orderNo){
		List<UserBagModel> ubms = couponOrderRes.getUserBagPlats();
		
		if(ubms != null){
			String userBagId = null;
			for(UserBagModel ubm:ubms){

				if(ubm.getBestUsed() != null && ubm.getBestUsed() == UserBagBestUsed.best.ordinal()){
					userBagId = ubm.getUserBagId();
				}
			}
			//1.修改使用平台券的状态为已使用
			userBagMapper.update(userBagId, UserBagStatus.unpay.ordinal());

			//2.券日志
			UserBagLogEntity userBagLogEntity = new UserBagLogEntity();
			userBagLogEntity.setType(UserBagLogType.unpay.ordinal());
			userBagLogEntity.setUserBagId(userBagId);
			userBagLogMapper.add(userBagLogEntity);

			//3.插入订单优惠券表
			UserBagOrderEntity userBagOrderEntity = new UserBagOrderEntity();
			userBagOrderEntity.setUserBagId(userBagId);
			userBagOrderEntity.setOrderNo(orderNo);
			userBagOrderMapper.add(userBagOrderEntity);
		}
	}
	
	public void updateSelfPlat(String userBagId, String orderNo){
		UserBagFindEntity ubfe = userBagMapper.queryBagDetail(userBagId);
		if(ubfe == null){
 			logger.error("优惠券自选失败={}");
 			throw new CouponException("S12");
		}
		//1.修改使用平台券的状态为已使用
		userBagMapper.update(userBagId, UserBagStatus.unpay.ordinal());

		//2.券日志
		UserBagLogEntity userBagLogEntity = new UserBagLogEntity();
		userBagLogEntity.setType(UserBagLogType.unpay.ordinal());
		userBagLogEntity.setUserBagId(userBagId);
		userBagLogMapper.add(userBagLogEntity);

		//3.插入订单优惠券表
		UserBagOrderEntity userBagOrderEntity = new UserBagOrderEntity();
		userBagOrderEntity.setUserBagId(userBagId);
		userBagOrderEntity.setOrderNo(orderNo);
		userBagOrderMapper.add(userBagOrderEntity);
	}
	
	
	public static List<UserBagFindEntity> mySortRepeat(List<UserBagFindEntity> list){
        HashMap<String,UserBagFindEntity> tempMap = Maps.newHashMap();
        for (UserBagFindEntity ubfe : list) {
            String key = ubfe.getCpId();
                tempMap.put(key, ubfe);
        }
        List<UserBagFindEntity> tempList = Lists.newArrayList();
        for(String key : tempMap.keySet()){
            tempList.add(tempMap.get(key));
        }
        return tempList;
	 }


}
