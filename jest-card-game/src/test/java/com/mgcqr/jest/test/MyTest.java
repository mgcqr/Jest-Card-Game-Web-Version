package com.mgcqr.jest.test;

import com.mgcqr.jest.entity.User;
import com.mgcqr.jest.mapper.UserUnionMapper;
import com.mgcqr.jest.mapper.UserMapper;
import com.mgcqr.jest.service.BasicService;
import com.mgcqr.jest.service.RedisCacheService;
import com.mgcqr.jest.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserUnionMapper userUnionMapper;

    @Autowired
    private BasicService basicService;
    @Autowired
    private RedisCacheService redisCacheService;

    @Test
    public void test(){

        System.out.println(basicService.count());

        User u = userMapper.selectById(123);
//        User u = userUnionMapper.findById(123);
        System.out.println(u);

        System.out.println("test");

    }


    @Test
    public void testUtil(){
        log.info("testing");
        List<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(100);

        try {
            String json = JsonUtil.toJsonString(list);
            System.out.println(json);

            List<Integer> parsedList = JsonUtil.toList(json, Integer.class);

            System.out.println(Arrays.toString(parsedList.toArray()));

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }


    }

    @Test
    public void testCache(){

        redisCacheService.add("key", 123);

        System.out.println(redisCacheService.getObject("key", Integer.class));

    }

}
