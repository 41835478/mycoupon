package com.cloudjet.coupon.exception;

public class CouponException extends RuntimeException{

	/**
	 * 优惠券异常
	 */
	private static final long serialVersionUID = 1L;
	
	private String errCode;
	private String errMsg;
	
	public CouponException() {
		super();
	}
	
	public CouponException(String message, Throwable cause){
		super(message, cause);
	}

	public CouponException(String errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public CouponException(String message) {
		super(message);
	}

	public CouponException(Throwable cause) {
		super(cause);
	}
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	

}
