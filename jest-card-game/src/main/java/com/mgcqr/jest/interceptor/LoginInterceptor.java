package com.mgcqr.jest.interceptor;

import com.mgcqr.jest.entity.User;
import com.mgcqr.jest.repository.RedisCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisCacheRepository redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if(token == null) {
            System.out.println("not logged in");
            return false;
        }

        if(redis.hasKey(token)){
            System.out.println("user logged in");
            System.out.println("token: " + token);
            redis.expireDefault(token);
            User currentUser = redis.getObject(token,User.class);
            UserContextHolder.set(currentUser);
            return true;
        }else {
            System.out.println("token missing or wrong");
            return false;
        }

    }
}
