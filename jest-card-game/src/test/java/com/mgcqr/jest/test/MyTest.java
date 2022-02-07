package com.mgcqr.jest.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mgcqr.jest.dto.LoginDto;
import com.mgcqr.jest.dto.PageRequestDto;
import com.mgcqr.jest.entity.GameEntity;
import com.mgcqr.jest.entity.UserEntity;
import com.mgcqr.jest.enumeration.GameState;
import com.mgcqr.jest.mapper.GameMapper;
import com.mgcqr.jest.mapper.UserUnionMapper;
import com.mgcqr.jest.mapper.UserMapper;
import com.mgcqr.jest.service.BasicService;
import com.mgcqr.jest.repository.RedisCacheRepository;
import com.mgcqr.jest.service.WaitingHallService;
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
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private WaitingHallService waitingHallService;

    @Test
    public void test(){

//        for (int i = 0; i < 5; i++){
//            waitingHallService.newGame();
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        PageRequestDto dto = new PageRequestDto();
        dto.setCurrent(1);
        dto.setSize(5);

        waitingHallService.getWaitingGameListPage(dto);



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

        UserEntity u = new UserEntity();
        BeanUtils.copyProperties(dto, u);
        System.out.println(u.toString());


    }

    @Test
    public void testMybatis(){
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUserName,"mgcqr")
                .eq(UserEntity::getPassWord, "Base64_UvGWbOfTrtRbUEd3QJAntg==");
        UserEntity u = userMapper.selectOne(wrapper);
        System.out.println(u);

    }

    @Test
    public void testMybatis_2(){
        LambdaQueryWrapper<GameEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GameEntity::getState, GameState.Waiting.toString())
                .orderByAsc(GameEntity::getStartTime);
        List<GameEntity> list = gameMapper.selectList(wrapper);
    }


}
