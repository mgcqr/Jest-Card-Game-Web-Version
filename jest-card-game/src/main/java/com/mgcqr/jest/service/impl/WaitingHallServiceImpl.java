package com.mgcqr.jest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mgcqr.jest.dto.IdRequestDto;
import com.mgcqr.jest.dto.NewGameResDto;
import com.mgcqr.jest.dto.PageRequestDto;
import com.mgcqr.jest.dto.PageResDto;
import com.mgcqr.jest.entity.GameEntity;
import com.mgcqr.jest.entity.GameUserRelEntity;
import com.mgcqr.jest.enumeration.GameState;
import com.mgcqr.jest.interceptor.UserContextHolder;
import com.mgcqr.jest.mapper.GameMapper;
import com.mgcqr.jest.mapper.GameUserRelMapper;
import com.mgcqr.jest.service.WaitingHallService;
import com.mgcqr.jest.util.KeyUtil;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WaitingHallServiceImpl implements WaitingHallService {


    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private GameUserRelMapper gameUserRelMapper;

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


    /**
     *
     * @param dto input game id
     * @return success or fail
     */
    @Override
    @Transactional
    public boolean joinGame(IdRequestDto dto){
        LambdaQueryWrapper<GameUserRelEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GameUserRelEntity::getGameId, dto.getId());
        Long userCount = gameUserRelMapper.selectCount(wrapper);
        if( userCount >= 3 )
            return false;

        GameUserRelEntity entity = new GameUserRelEntity();
        entity.setGameId(dto.getId());
        entity.setUserId(UserContextHolder.getId());

        wrapper.clear();
        wrapper.eq(GameUserRelEntity::getGameId, dto.getId())
                .eq(GameUserRelEntity::getUserId, entity.getUserId());
        if (gameUserRelMapper.selectCount(wrapper) == 0){//不存在才添加 方法要求幂等
            entity.setId(KeyUtil.getKey());
            gameUserRelMapper.insert(entity);
        }

        if (userCount == 2){
            //插入后满员
            //自动开始游戏
            GameEntity game = gameMapper.selectById(dto.getId());
            game.setState(GameState.Running);
            gameMapper.updateById(game);

            //TODO call game start method
        }

        return true;
    }

    /**
     *
     * @param dto input game id
     * @return success or fail
     */
    @Override
    @Transactional
    public boolean quitGame(IdRequestDto dto){
        GameEntity game = gameMapper.selectById(dto.getId());
        //等待阶段才可退出
        if(game.getState() != GameState.Waiting)
            return false;

        LambdaQueryWrapper<GameUserRelEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GameUserRelEntity::getGameId, dto.getId())
                .eq(GameUserRelEntity::getUserId, UserContextHolder.getId());
        gameUserRelMapper.delete(wrapper);

        return true;
    }



}
