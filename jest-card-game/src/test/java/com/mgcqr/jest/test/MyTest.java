package com.mgcqr.jest.test;

import com.mgcqr.jest.entity.User;
import com.mgcqr.jest.mapper.UserUnionMapper;
import com.mgcqr.jest.mapper.UserMapper;
import com.mgcqr.jest.service.BasicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserUnionMapper userUnionMapper;

    @Autowired
    private BasicService basicService;

    @Test
    public void test(){

        System.out.println(basicService.count());

        User u = userMapper.selectById(123);
//        User u = userUnionMapper.findById(123);
        System.out.println(u);

        System.out.println("test");

    }
}
