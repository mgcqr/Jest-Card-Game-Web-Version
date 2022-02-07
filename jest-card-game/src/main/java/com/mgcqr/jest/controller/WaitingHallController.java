package com.mgcqr.jest.controller;

import com.mgcqr.jest.dto.NewGameResDto;
import com.mgcqr.jest.dto.PageRequestDto;
import com.mgcqr.jest.dto.PageResDto;
import com.mgcqr.jest.dto.ResponseWrapper;
import com.mgcqr.jest.entity.GameEntity;
import com.mgcqr.jest.service.WaitingHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waiting-hall")
public class WaitingHallController {

    @Autowired
    private WaitingHallService waitingHallService;

    @PostMapping("/new-game")
    public ResponseWrapper<NewGameResDto> newGame(){
        NewGameResDto dto = waitingHallService.newGame();
        return new ResponseWrapper<>(dto);
    }

    @GetMapping("/list")
    public ResponseWrapper<PageResDto<GameEntity>> getWaitingGameListPage(@RequestParam("current") Integer current,
                                                                          @RequestParam("size") Integer size){
        PageRequestDto dto = new PageRequestDto();
        dto.setSize(size);
        dto.setCurrent(current);
        return new ResponseWrapper<>(waitingHallService.getWaitingGameListPage(dto));
    }

}
