package com.mgcqr.jest.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mgcqr.jest.core.AsyncTest;
import com.mgcqr.jest.entity.UserEntity;
import com.mgcqr.jest.mapper.GameMapper;
import com.mgcqr.jest.mapper.UserUnionMapper;
import com.mgcqr.jest.mapper.UserMapper;
import com.mgcqr.jest.repository.RedisCacheRepository;
import com.mgcqr.jest.service.WaitingHallService;
import com.mgcqr.jest.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserUnionMapper userUnionMapper;

    @Autowired
    private RedisCacheRepository redisCacheRepository;
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private WaitingHallService waitingHallService;
    @Autowired
    private AsyncTest asyncTest;
    @Autowired
    private WebSocketServer server;

    @Test
    public void test(){


        UserEntity user = new UserEntity();
        user.setId("f7757c347a584c32b47434d6b00684e7");
        user.setUserName("mgcqr_gggg");

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUserName, user.getUserName());

        userMapper.update(user, wrapper);

        System.out.println("test");
    }

    @Test
    public void async(){
        for (int i = 0; i < 10; i++){
            asyncTest.task(i);
        }
        System.out.println("tasks started");
    }


}
