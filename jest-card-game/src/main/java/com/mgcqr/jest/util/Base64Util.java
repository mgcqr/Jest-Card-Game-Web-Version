package com.mgcqr.jest.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class Base64Util {
    private static final BASE64Encoder encoder = new BASE64Encoder();
    private static final BASE64Decoder decoder = new BASE64Decoder();

    //字节数组转Base64编码
    public static String byte2String(byte[] bytes){
        return encoder.encode(bytes);
    }

    //Base64编码转字节数组
    public static byte[] String2Byte(String base64Key) throws IOException {
        return decoder.decodeBuffer(base64Key);
    }
}
