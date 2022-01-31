package com.mgcqr.jest.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mgcqr.jest.dto.LoginDto;
import com.mgcqr.jest.entity.User;
import com.mgcqr.jest.mapper.UserUnionMapper;
import com.mgcqr.jest.mapper.UserMapper;
import com.mgcqr.jest.service.BasicService;
import com.mgcqr.jest.repository.RedisCacheRepository;
import com.mgcqr.jest.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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
    private RedisCacheRepository redisCacheRepository;

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
    public void testRedis(){


        redisCacheRepository.setHashCache("500", "1", "3");
        redisCacheRepository.setHashCache("500", "2", "4");
        redisCacheRepository.setHashCache("500", "2", "4");

        Integer val = redisCacheRepository.getHashCache(1, "2", Integer.class);

        System.out.println(val);



//        redisCacheService.set(123, 345);


    }

    @Test
    public void testBeanUtil(){
        LoginDto dto = new LoginDto();
        dto.setUserName("name");
        dto.setPassWord("psw");

        User u = new User();
        BeanUtils.copyProperties(dto, u);
        System.out.println(u.toString());


    }

    @Test
    public void testMybatis(){
        QueryWrapper<User> wrapper;
        Map<String, Object> param;
        User u;

        wrapper = new QueryWrapper<>();
//        param = new HashMap<>();
//        param.put("user_name", "mgcqr");
//        param.put("pass_word", "12345");
        wrapper.eq("user_name","mgcqr")
                .eq("pass_word", "12345");
        u = userMapper.selectOne(wrapper);
        System.out.println(u);

//        wrapper = new QueryWrapper<>();
//        param = new HashMap<>();
//        param.put("name", "mgcqr");
//        param.put("pass_word", "123456");
//        wrapper.allEq(param);
//        u = userMapper.selectOne(wrapper);

    }

}
