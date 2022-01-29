package com.mgcqr.jest.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if(token == null) {
            System.out.println("not logged in");
            return false;
        }

        HttpSession session = request.getSession();
        String sessionToken = (String) session.getAttribute("token");
        if(sessionToken != null && sessionToken.equals(token)){
            System.out.println("user logged in");
            System.out.println("token: " + token);
            return true;
        }else {
            System.out.println("token missing or wrong");
            return false;
        }

    }
}
