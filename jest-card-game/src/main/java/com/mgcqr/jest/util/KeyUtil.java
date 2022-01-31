package com.mgcqr.jest.util;

import java.util.UUID;

public class KeyUtil {


    public static String getKey(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
