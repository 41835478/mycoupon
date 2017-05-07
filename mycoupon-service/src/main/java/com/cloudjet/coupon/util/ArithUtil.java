package com.cloudjet.coupon.util;

import java.math.BigDecimal;

public class ArithUtil {
	
	/** 
     * 提供精确的加法运算。 
     * @param v1 
     * @return 两个参数的和 
     */  
  
    public static double add(double v1, double v2) {  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.add(b2).doubleValue();  
    } 
    
    /** 
     * 提供精确的减法运算。 
     * @param v1 
     * @param v2 
     * @return 两个参数的差 
     */  
    public static double sub(double v1, double v2) {  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.subtract(b2).doubleValue();  
    } 
	
    /** 
     * 提供精确的乘法运算。 
     * @param v1 
     * @param v2 
     * @return 两个参数的积 
     */  
  
    public static double mul(double v1, double v2) {  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.multiply(b2).doubleValue();  
    } 
	
    
    
    public static void main(String[] args){
    	
    	Double a = 30.00;
    	Integer b = 2;
    	
    	Double c = ArithUtil.mul(a, b);
    	
    	System.out.println(c);
    	
    }
}
