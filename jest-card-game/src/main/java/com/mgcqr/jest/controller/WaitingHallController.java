package com.mgcqr.jest.controller;

import com.mgcqr.jest.dto.*;
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

    @PutMapping("/join_game")
    public ResponseWrapper<String> joinGame(@RequestBody IdRequestDto dto){
        if(waitingHallService.joinGame(dto)){
            return new ResponseWrapper<>("join success");
        }else {
            return new ResponseWrapper<>( "join error");
        }
    }

    @DeleteMapping("/quit_game")
    public ResponseWrapper<String> quitGame(@RequestBody IdRequestDto dto){
        if(waitingHallService.quitGame(dto)){
            return new ResponseWrapper<>("quit success");
        }else {
            return new ResponseWrapper<>( "quit error: game started");
        }
    }

}
