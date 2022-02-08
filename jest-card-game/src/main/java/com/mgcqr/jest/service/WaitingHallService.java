package com.mgcqr.jest.service;

import com.mgcqr.jest.dto.IdRequestDto;
import com.mgcqr.jest.dto.NewGameResDto;
import com.mgcqr.jest.dto.PageRequestDto;
import com.mgcqr.jest.dto.PageResDto;
import com.mgcqr.jest.entity.GameEntity;
import org.springframework.transaction.annotation.Transactional;

public interface WaitingHallService {
    NewGameResDto newGame();


    PageResDto<GameEntity> getWaitingGameListPage(PageRequestDto requestDto);

    @Transactional
    boolean joinGame(IdRequestDto dto);

    @Transactional
    boolean quitGame(IdRequestDto dto);
}
