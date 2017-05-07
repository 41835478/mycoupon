package com.cloudjet.coupon.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CDKeyUtils {
	
	/**
	 * 兑换码生成方法
	 * 
	 * */
	public static String getCDKey(){
		//随机出数字
		int a = (int)(Math.random()*900000);
		//12为减去随机数字码的长度
		int aLength = 12-Integer.toString(a).length();
		//随机出剩下的字母
		String[] originStr = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",  
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
                "W", "X", "Y", "Z" };
		List<String> originList = Arrays.asList(originStr); 
		Collections.shuffle(originList);
		StringBuilder sb = new StringBuilder();   
        for (int i = 0; i < aLength; i++) {  
            sb.append(originList.get(i));  
        } 
        String resultBefore = Integer.toString(a)+sb.toString();
        char[] temp = resultBefore.toCharArray();
       
        List<String> strList = new ArrayList<String>();
        //字符串转化为集合
        for(int j = 0; j < temp.length; j++){
        	strList.add(String.valueOf(temp[j]));
        }
        Collections.shuffle(strList);
        
        StringBuilder sbResult = new StringBuilder();  
        for (int i = 0; i < strList.size(); i++) {  
        	sbResult.append(strList.get(i));  
        }
		
		return sbResult.toString();
	}
	
	public static void main(String[] args) {
		
		for(int i=0;i<50000;i++){
			String s = CDKeyUtils.getCDKey();
			System.out.println(s);	
		}
		
		

	}
	
}
