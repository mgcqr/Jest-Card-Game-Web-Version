package com.mgcqr.jest.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * 前后端数据传输加密工具类
 * @author monkey
 *
 */
public class AesEncryptUtil {
    //可配置到Constant中，并读取配置文件注入,16位,自己定义
    private static final String KEY = "qw@1fghyKKui895T";

    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 加密
     * @param content 加密的字符串
     * @param encryptKey key值
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String encryptKey) throws Exception {
        return encrypt(Base64Util.String2Byte(content), encryptKey);

    }
    public static String encrypt(byte[] content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        byte[] b = cipher.doFinal(content);
        // 采用base64算法进行转码,避免出现中文乱码
        return Base64Util.byte2String(b);

    }

//    /**
//     * 解密
//     * @param encryptStr 解密的字符串
//     * @param decryptKey 解密的key值
//     * @return
//     * @throws Exception
//     */
//    public static String decrypt(String encryptStr, String decryptKey) throws Exception {
//        KeyGenerator kgen = KeyGenerator.getInstance("AES");
//        kgen.init(128);
//        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
//        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
//        // 采用base64算法进行转码,避免出现中文乱码
//        byte[] encryptBytes = Base64Util.base642Byte(encryptStr);
//        byte[] decryptBytes = cipher.doFinal(encryptBytes);
//        return new String(decryptBytes);
//    }

    public static String encrypt(String content) throws Exception {
        return encrypt(content, KEY);
    }
    public static String encrypt(byte[] content) throws Exception {
        return encrypt(content, KEY);
    }
//    public static String decrypt(String encryptStr) throws Exception {
//        return decrypt(encryptStr, KEY);
//    }
}