package com.mgcqr.jest.core.stuff;

import com.mgcqr.jest.core.dto.MailBoxDto;

public class MailBox {

    private MailBoxDto message;
    private boolean full = false;

    public synchronized void produce(MailBoxDto msg){
        if(full) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        message = msg;
        full = true;
        this.notify();
    }

    public synchronized<T extends MailBoxDto> T consume(Class<T> type){
        T res;
        while (true){
            if(!full) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            full = false;
            if(type.isInstance(message)){
                res = (T) message;
                break;
            }
            this.notify();
        }
        return res;
    }

}
