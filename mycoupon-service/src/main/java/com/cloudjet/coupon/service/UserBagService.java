package com.cloudjet.coupon.service;


import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.model.ConvertSuccessModel;
import com.cloudjet.coupon.model.CouponInfoModel;
import com.cloudjet.coupon.model.PackagePullModel;
import com.cloudjet.coupon.model.UserBagModel;
import com.cloudjet.coupon.model.UserBagPageModel;
import com.cloudjet.coupon.model.UserPackagePullModel;
import com.cloudjet.coupon.request.CouponVerifyRequest;
import com.cloudjet.coupon.res.model.ShareCodeResModel;
import com.cloudjet.coupon.util.PageResult;

public interface UserBagService {

	
    /**
     * 用户券礼包领取（旧方法）
     * @param 
     * @return
     * @throws CouponException
     */
	@Deprecated
    void pullUserPackage(UserPackagePullModel userPackagePullModel) throws CouponException;
    
    /** 分页查询
	 * @return  PageResult
	 * @param userBagPageModel
     */
    PageResult<UserBagModel> queryList(UserBagPageModel userBagPageModel);
    
	/** 优惠券状态修改
     *  @param String orderNo,手机号  tel 
     * */
    void alterStatus(String orderNo,String tel); 
    
    
    /** 领取优惠券(通用)
     *  @param 手机号  userTel 
     *  @param  cpid 优惠券id
     *  @param shareCode 分享码
     *  @return ShareCodeResModel
	 * @throws CouponException 
     * */
    ShareCodeResModel pullCoupon(String userTel,String cpId, String shareCode) throws CouponException;
    
    /**
     * 用户券礼包领取
     * @parm packagePullModel
     * @return ShareCodeResModel
     */
    ShareCodeResModel packagePull(PackagePullModel packagePullModel) throws CouponException;
  
    /**
     * 在券包中查询出优惠券详情
     * @param cpId
     * @param tel
     */
    CouponInfoModel queryBagCoupon(String userBagId, String tel) throws CouponException;
        
    /**
     * 兑换码兑换优惠券
     * @param code
     * @param tel
     * @return
     * @throws CouponException
     */
    ConvertSuccessModel convertCoupon(String code, String tel) throws CouponException;
    
    /**
     * 线下券核销
     * @param CouponVerifyRequest
     * */
    void verifyCoupon(CouponVerifyRequest couponVerifyRequest) throws CouponException;
}
