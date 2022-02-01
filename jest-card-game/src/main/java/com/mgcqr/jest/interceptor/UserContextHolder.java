package com.mgcqr.jest.interceptor;

import com.mgcqr.jest.entity.User;

public class UserContextHolder {
    private static final ThreadLocal<User> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();


    public static User get() {
        return USER_INFO_THREAD_LOCAL.get();
    }

    protected static void set(User user) {
        USER_INFO_THREAD_LOCAL.set(user);
    }

    protected static void clear() {
        USER_INFO_THREAD_LOCAL.remove();
    }

}
