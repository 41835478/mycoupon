package com.cloudjet.coupon.util;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.mysql.jdbc.StringUtils;

public class DesUtils {
	
	/*
     * 生成密钥
     */
    public static byte[] initKey() throws Exception{
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
    }

    
    /*
     * DES 加密
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception{
        SecretKey secretKey = new SecretKeySpec(key, "DES");
        
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherBytes = cipher.doFinal(data);
        return cipherBytes;
    }
    
    
    /*
     * DES 解密
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception{
        SecretKey secretKey = new SecretKeySpec(key, "DES");
        
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] plainBytes = cipher.doFinal(data);
        return plainBytes;
    }

    //Test
    public static void main(String[] args) throws Exception {
    	byte[] desKey = DesUtils.initKey();
       
    	String str = "15136219337-0e663c7cdd8711e68d38ecf4bbd34bf4-0-1486532838432";
    	
        byte[] desResult = DesUtils.encrypt(str.getBytes(), desKey);
        System.out.println(str + ">>>DES 加密结果>>>" + StringUtils.toAsciiString(desResult));
        
        byte[] desPlain = DesUtils.decrypt(desResult, desKey);
        System.out.println(">>>DES 解密结果>>>" + new String(desPlain));
    }
    
	
}
