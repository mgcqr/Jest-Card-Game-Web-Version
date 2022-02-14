package com.mgcqr.jest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mgcqr.jest.core.GameRunner;
import com.mgcqr.jest.core.dto.GameResultDto;
import com.mgcqr.jest.core.dto.UserGameResult;
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
import com.mgcqr.jest.model.RuntimeUserInfo;
import com.mgcqr.jest.service.WaitingHallService;
import com.mgcqr.jest.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WaitingHallServiceImpl implements WaitingHallService {


    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private GameUserRelMapper gameUserRelMapper;
    @Autowired
    private GameRunner gameRunner;

    @Override
    public NewGameResDto newGame(String gameDescription){
        NewGameResDto res = new NewGameResDto();
        String gameId = KeyUtil.getKey();
        res.setGameId(gameId);

        GameEntity entity = new GameEntity();
        entity.setId(gameId);
        entity.setDescription(gameDescription);
        entity.setState(GameState.Waiting);
        entity.setStartTime(LocalDateTime.now());
        gameMapper.insert(entity);
        return res;
    }


    @Override
    public PageResDto<GameEntity> getWaitingGameListPage(PageRequestDto requestDto){
        LambdaQueryWrapper<GameEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GameEntity::getState, GameState.Waiting.toString())
                .orderByDesc(GameEntity::getStartTime);
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
        GameEntity game = gameMapper.selectById(dto.getId());
        if(game == null)//game doesn't exist
            return false;

        LambdaQueryWrapper<GameUserRelEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GameUserRelEntity::getGameId, dto.getId());
        List<GameUserRelEntity> usersInGame = gameUserRelMapper.selectList(wrapper);

        if( usersInGame.size() >= 3 )
            return false;

        boolean hasCurrentUser = false;
        for(GameUserRelEntity entity : usersInGame){
            if(entity.getUserId().equals(UserContextHolder.getId())) {
                hasCurrentUser = true;
                break;
            }
        }
        if (!hasCurrentUser){//不存在才添加 方法要求幂等
            GameUserRelEntity entity = new GameUserRelEntity();
            entity.setGameId(dto.getId());
            entity.setUserId(UserContextHolder.getId());
            entity.setId(KeyUtil.getKey());
            gameUserRelMapper.insert(entity);
        }

        if (usersInGame.size() == 2){
            //插入后满员
            //自动开始游戏
            game.setState(GameState.Running);
            gameMapper.updateById(game);
            List<RuntimeUserInfo> users = new ArrayList<>();
            for(GameUserRelEntity entity : usersInGame){
                RuntimeUserInfo info = new RuntimeUserInfo();
                BeanUtils.copyProperties(entity, info);
                users.add(info);
            }
            users.add(UserContextHolder.get());
            gameRunner.runGame(game.getId(), users);
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

        wrapper.clear();
        wrapper.eq(GameUserRelEntity::getGameId, dto.getId());
        long userInGameCount = gameUserRelMapper.selectCount(wrapper);
        if(userInGameCount == 0){
            //最后一人退出, 删除游戏
            gameMapper.deleteById(dto.getId());
        }

        return true;
    }

    @Override
    @Transactional
    public void finishGame(String gameId, GameResultDto gameResultDto){
        GameEntity entity = gameMapper.selectById(gameId);
        entity.setState(GameState.Closed);
        entity.setFinishTime(LocalDateTime.now());
        gameMapper.updateById(entity);
        if(gameResultDto != null){
            LambdaQueryWrapper<GameUserRelEntity> wrapper = new LambdaQueryWrapper<>();
            for(UserGameResult res : gameResultDto.getResults()){
                wrapper.eq(GameUserRelEntity::getGameId, gameId)
                        .eq(GameUserRelEntity::getUserId, res.getUserId());
                GameUserRelEntity relEntity = gameUserRelMapper.selectOne(wrapper);
                relEntity.setScore(res.getScore());
                gameUserRelMapper.updateById(relEntity);
                wrapper.clear();
            }
        }
    }
}
