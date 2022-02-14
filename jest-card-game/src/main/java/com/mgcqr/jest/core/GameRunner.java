package com.mgcqr.jest.core;

import com.mgcqr.jest.core.dto.InitialInfoDto;
import com.mgcqr.jest.core.stuff.MailBox;
import com.mgcqr.jest.core.stuff.Table;
import com.mgcqr.jest.websocket.WebSocketRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRunner {

    @Autowired
    private WebSocketRouter webSocketRouter;

    @Async
    public void runGame(String gameId, List<String> userIds){
        CoreInterface coreInterface = new CoreInterface();

        //registerGame fail
        if(! webSocketRouter.registerGame(userIds, coreInterface)) {
            closeGame(gameId);
            return;
        }

        Table table = new Table(gameId);
        MailBox mailBox = table.getMailBox();

        table.play();

        InitialInfoDto initial = new InitialInfoDto();
        initial.setUserIds(userIds);
        mailBox.produce(initial);

        //TODO take card输入校验


    }


    public void closeGame(String gameId){

    }
}
