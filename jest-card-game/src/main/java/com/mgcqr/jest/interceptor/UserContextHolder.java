package com.mgcqr.jest.interceptor;

import com.mgcqr.jest.model.RuntimeUserInfo;

public class UserContextHolder {
    private static final ThreadLocal<RuntimeUserInfo> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();


    public static RuntimeUserInfo get() {
        return USER_INFO_THREAD_LOCAL.get();
    }

    public static String getId(){
        return get().getId();
    }

    public static String getUserName(){
        return get().getUserName();
    }

    protected static void set(RuntimeUserInfo user) {
        USER_INFO_THREAD_LOCAL.set(user);
    }

    protected static void clear() {
        USER_INFO_THREAD_LOCAL.remove();
    }

}
