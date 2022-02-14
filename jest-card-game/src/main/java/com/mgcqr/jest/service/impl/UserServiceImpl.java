package com.mgcqr.jest.service.impl;

import com.mgcqr.jest.model.RuntimeUserInfo;
import com.mgcqr.jest.repository.RedisCacheRepository;
import com.mgcqr.jest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisCacheRepository redis;


    @Override
    public String getId(String token){
        RuntimeUserInfo info = redis.getObject(token, RuntimeUserInfo.class);
        return (info == null) ? null : info.getId();
    }

    @Override
    public String getUserName(String token){
        RuntimeUserInfo info = redis.getObject(token, RuntimeUserInfo.class);
        return (info == null) ? null : info.getUserName();
    }
}
