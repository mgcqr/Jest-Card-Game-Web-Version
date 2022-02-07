package com.mgcqr.jest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mgcqr.jest.dto.NewGameResDto;
import com.mgcqr.jest.dto.PageRequestDto;
import com.mgcqr.jest.dto.PageResDto;
import com.mgcqr.jest.entity.GameEntity;
import com.mgcqr.jest.enumeration.GameState;
import com.mgcqr.jest.mapper.GameMapper;
import com.mgcqr.jest.service.WaitingHallService;
import com.mgcqr.jest.util.KeyUtil;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WaitingHallServiceImpl implements WaitingHallService {


    @Autowired
    private GameMapper gameMapper;

    @Override
    public NewGameResDto newGame(){
        NewGameResDto res = new NewGameResDto();
        String gameId = KeyUtil.getKey();
        res.setGameId(gameId);

        GameEntity entity = new GameEntity();
        entity.setId(gameId);
        entity.setState(GameState.Waiting);
        entity.setStartTime(LocalDateTime.now());
        gameMapper.insert(entity);
        return res;
    }


    @Override
    public PageResDto<GameEntity> getWaitingGameListPage(PageRequestDto requestDto){
        LambdaQueryWrapper<GameEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GameEntity::getState, GameState.Waiting.toString())
                .orderByAsc(GameEntity::getStartTime);
        Page<GameEntity> page = new Page<>(requestDto.getCurrent(), requestDto.getSize());
        page = gameMapper.selectPage(page,wrapper);
        PageResDto<GameEntity> res = new PageResDto<>();
        BeanUtils.copyProperties(page, res);
        return res;
    }


    //TODO join game


    //TODO leave game



}
