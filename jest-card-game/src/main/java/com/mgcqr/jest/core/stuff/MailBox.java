package com.mgcqr.jest.core.stuff;

public class MailBox {

    private Object message;
    private boolean full;

    public synchronized void produce(Object msg){
        message = msg;
        full = true;
    }
    public synchronized Object consume(){
        full = false;
        return message;
    }
    public synchronized boolean isFull(){
        return full;
    }

}
