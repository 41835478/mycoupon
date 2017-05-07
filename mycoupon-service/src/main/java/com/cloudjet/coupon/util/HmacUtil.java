package com.cloudjet.coupon.util;
import java.security.Security;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;


public class HmacUtil {
	
	/** 
	* 初始化HmacSHA256的密钥 
	* @return byte[] 密钥 
	*  
	* */  
   public static byte[] initHmacSHA256Key() throws Exception{  
		Security.addProvider(new BouncyCastleProvider());  
		//初始化KeyGenerator  
		KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA256");  
		//产生密钥  
		SecretKey secretKey=keyGenerator.generateKey();  
		//获取密钥  
		return secretKey.getEncoded();  
	}
   
   /** 
    * HmacSHA256消息摘要 
    * @param data 待做摘要处理的数据 
    * @param key 密钥 
    * @return  byte[] 消息摘要 
    * */  
   public static byte[] encodeHmacSHA256(byte[] data,byte[] key) throws Exception{  
       //加入BouncyCastleProvider的支持  
       Security.addProvider(new BouncyCastleProvider());  
       //还原密钥，因为密钥是以byte形式为消息传递算法所拥有  
       SecretKey secretKey=new SecretKeySpec(key,"HmacSHA256");  
       //实例化Mac  
       Mac mac=Mac.getInstance(secretKey.getAlgorithm());  
       //初始化Mac  
       mac.init(secretKey);  
       //执行消息摘要处理  
       return mac.doFinal(data);  
   }  
   
   /** 
    * 初始化HmacSHA1的密钥 
    * @return byte[] 密钥 
    *  
    * */  
   public static byte[] initHmacSHAKey() throws Exception{  
       //初始化KeyGenerator  
       KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA1");  
       //产生密钥  
       SecretKey secretKey=keyGenerator.generateKey();  
       //获取密钥  
       return secretKey.getEncoded();  
   }
   
   /** 
    * HmacSHA1消息摘要 
    * @param data 待做摘要处理的数据 
    * @param key 密钥 
    * @return  byte[] 消息摘要 
    * */  
   public static byte[] encodeHmacSHA(byte[] data,byte[] key) throws Exception{  
       //还原密钥，因为密钥是以byte形式为消息传递算法所拥有  
       SecretKey secretKey=new SecretKeySpec(key,"HmacSHA1");  
       //实例化Mac  
       Mac mac=Mac.getInstance(secretKey.getAlgorithm());  
       //初始化Mac  
       mac.init(secretKey);  
       //执行消息摘要处理  
       return mac.doFinal(data);  
   } 
   
   /**
    * 生成加密字符串 SHA1
    * */
   public static String getHmacSHA(String str){
	    
	   String datahex = null;
		try {
			byte[] key = HmacUtil.initHmacSHAKey();
			 //获取摘要信息  
		     byte[] data=HmacUtil.encodeHmacSHA(str.getBytes(), key);  
		     datahex=new String(Hex.encode(data)); 
		} catch (Exception e) {
			e.printStackTrace();
		}  
	   
	   return datahex;
   }
   public static void main(String[] args){
	   
	   String str = "haowenlong";
	 
     try {
    	 
    	//初始化密钥  
         byte[] key2=HmacUtil.initHmacSHA256Key();  
         //获取摘要信息  
         byte[] data2=HmacUtil.encodeHmacSHA256(str.getBytes(), key2);  
         String datahex2=new String(Hex.encode(data2));
    	 
         System.out.println("Bouncycastle HmacSHA256的密钥:"+key2.toString());  
         System.out.println("Bouncycastle HmacSHA256算法摘要："+data2.toString());  
         System.out.println("Bouncycastle HmacSHA256算法摘要HEX："+datahex2);  
         System.out.println(); 
        
        
        //初始化密钥  
        byte[] key3=HmacUtil.initHmacSHAKey();  
        //获取摘要信息  
        byte[] data3=HmacUtil.encodeHmacSHA(str.getBytes(), key3);  
        String datahex3=new String(Hex.encode(data3));  
        System.out.println("Bouncycastle HmacSHA1的密钥:"+key3.toString());  
        System.out.println("Bouncycastle HmacSHA1算法摘要："+data3.toString());  
        System.out.println("Bouncycastle HmacSHA1算法摘要HEX："+datahex3);  
        
        System.out.println("------------------------");
        System.out.println(HmacUtil.getHmacSHA("haowenlong"));
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	   
	   
	   
	   
   }
	
}
