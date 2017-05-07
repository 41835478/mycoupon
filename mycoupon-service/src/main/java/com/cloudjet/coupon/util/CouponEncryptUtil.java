package com.cloudjet.coupon.util;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Base64;

import com.mysql.jdbc.StringUtils;

public class CouponEncryptUtil {
	
	public static final int KEY = 100;
	
	/**
	 * 加密
	 * */
	public static String encrypt(String str){
		
		try {
		
			byte[] bArray = StringUtils.getBytes(str, "UTF-8");
			byte[] bArrayResult =  Base64.encode(bArray);
			
			String strResult =  StringUtils.toString(bArrayResult);
			return strResult;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 解密
	 * */
	public static String decode(String str){
		
		return StringUtils.toString(Base64.decode(str));
	}
	
	public static void main(String[] args){
		System.out.println(CouponEncryptUtil.encrypt("15136219337-0e663c7cdd8711e68d38ecf4bbd34bf4-0-1486532838432"));
		System.out.println(CouponEncryptUtil.decode(CouponEncryptUtil.encrypt("15136219337-0e663c7cdd8711e68d38ecf4bbd34bf4-0-1486532838432")));
		
	}
	
	
	
}
