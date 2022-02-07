package com.mgcqr.jest.service;

import com.mgcqr.jest.dto.NewGameResDto;
import com.mgcqr.jest.dto.PageRequestDto;
import com.mgcqr.jest.dto.PageResDto;
import com.mgcqr.jest.entity.GameEntity;

public interface WaitingHallService {
    NewGameResDto newGame();


    PageResDto<GameEntity> getWaitingGameListPage(PageRequestDto requestDto);
}
