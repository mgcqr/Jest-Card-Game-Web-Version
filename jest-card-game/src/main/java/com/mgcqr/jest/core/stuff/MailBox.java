package com.mgcqr.jest.core.stuff;

public class MailBox {

    private static Object message = null;

    public static synchronized Object consume() {
        Object o = message;
        message = null;
        return o;
    }

    public static synchronized void produce(Object o) {
        message = o;
    }




}
