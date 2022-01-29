package com.mgcqr.jest.service.impl;

import com.mgcqr.jest.mapper.UserUnionMapper;
import com.mgcqr.jest.entity.User;
import com.mgcqr.jest.mapper.single.UserMapper;
import com.mgcqr.jest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserUnionMapper userUnionMapper;

    @Override
    public void test(){
        User user = new User();
        user.setId(123);
        user.setName("amg");

//        userMapper.insert(user);
//        User u = userMapper.selectById(123);

        User u = userUnionMapper.findById(123);
        System.out.println(u);
    }
}
