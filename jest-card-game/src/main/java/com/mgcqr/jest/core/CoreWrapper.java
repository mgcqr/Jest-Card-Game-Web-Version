package com.mgcqr.jest.core;

import com.mgcqr.jest.core.stuff.MailBox;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CoreWrapper {

    //set by runGame()
    private static final ThreadLocal<CoreInterface> CORE_INTERFACE = new ThreadLocal<>();
    //get from Table
    private static final ThreadLocal<MailBox> MAIL_BOX = new ThreadLocal<>();


    @Async
    public void runGame(String gameId){

    }
}
