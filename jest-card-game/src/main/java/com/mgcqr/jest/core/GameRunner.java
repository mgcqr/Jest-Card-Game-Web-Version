package com.mgcqr.jest.core;

import com.mgcqr.jest.core.stuff.MailBox;
import com.mgcqr.jest.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRunner {

    //set by runGame()
    private static final ThreadLocal<CoreInterface> CORE_INTERFACE = new ThreadLocal<>();
    //get from Table
    private static final ThreadLocal<MailBox> MAIL_BOX = new ThreadLocal<>();

    @Autowired
    private WebSocketServer webSocketServer;

    @Async
    public void runGame(String gameId, List<String> userIds){
        CoreInterface coreInterface = new CoreInterface();

        webSocketServer.registerGame(userIds, coreInterface);



    }
}
