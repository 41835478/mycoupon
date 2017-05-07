package com.cloudjet.coupon.sdk;

public class CouponException extends Exception {

	/**
	 * 优惠异常。
	 * @author haowenlong
	 * @since 1.0, July 18, 2016
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String errCode;
	private String errMsg;
	
	public CouponException() {
		super();
	}

	public CouponException(String message, Throwable cause) {
		super(message, cause);
	}

	public CouponException(String message) {
		super(message);
	}

	public CouponException(Throwable cause) {
		super(cause);
	}

	public CouponException(String errCode, String errMsg) {
		super(errCode + ":" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

}
