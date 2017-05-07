package com.cloudjet.coupon.util;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Base64;

import com.mysql.jdbc.StringUtils;

public class Base64Util {
	
	public static void main(String[] args){
		
		String str = "15136219337-0e663c7cdd8711e68d38ecf4bbd34bf4-0-1486532838432";
		
		try {
			byte[] bArray = StringUtils.getBytes(str, "UTF-8");
			
			byte[] bArrayResult =  Base64.encode(bArray);
			
			String strResult =  StringUtils.toString(bArrayResult);
			
			System.out.println(strResult);
			
			System.out.println(StringUtils.toString(Base64.decode(strResult)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
