package com.mgcqr.jest.core.stuff;

public class MailBox {

    private Object message = null;

    public synchronized Object consume() {
        Object o = message;
        message = null;
        return o;
    }

    public synchronized void produce(Object o) {
        message = o;
    }




}
