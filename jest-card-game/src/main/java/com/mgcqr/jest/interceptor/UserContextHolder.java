package com.mgcqr.jest.interceptor;

import com.mgcqr.jest.model.CurrentUserInfo;

public class UserContextHolder {
    private static final ThreadLocal<CurrentUserInfo> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();


    public static CurrentUserInfo get() {
        return USER_INFO_THREAD_LOCAL.get();
    }

    public static String getId(){
        return get().getId();
    }

    public static String getUserName(){
        return get().getUserName();
    }

    protected static void set(CurrentUserInfo user) {
        USER_INFO_THREAD_LOCAL.set(user);
    }

    protected static void clear() {
        USER_INFO_THREAD_LOCAL.remove();
    }

}
