package com.mgcqr.jest.service.impl;

import com.mgcqr.jest.entity.UserEntity;
import com.mgcqr.jest.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BasicServiceImpl {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void debug(){
//        UserEntity user = new UserEntity();
//        user.setId("test");
//        user.setUserName("name");
//        user.setPassWord("password");
//
//        userMapper.insert(user);
        System.out.println("basic service");
    }

}
