package com.cloudjet.coupon.service;


import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.model.CouponOrderModel;
import com.cloudjet.coupon.model.CouponOrderResModel;

public interface OrderService {
	
	/**
	 * 订单结算调用
	 * @throws CouponException 
	 * */
	public CouponOrderResModel place(CouponOrderModel couponOrderModel) throws CouponException;
	
	
	/**
	 * 订单去付款
	 * @param couponOrderModel   必须传入主订单号
	 * @return 
	 * @throws CouponException 
	 * 
	 * */
	public CouponOrderResModel payPre(CouponOrderModel couponOrderModel) throws CouponException;
	
}
