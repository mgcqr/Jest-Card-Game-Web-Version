package com.mgcqr.jest.core.stuff;

public class MailBox {

    private Object message;
    private boolean full = false;

    public synchronized void produce(Object msg){
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

    public synchronized Integer consumeInt(){
        int res;
        while (true){
            if(!full) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            full = false;
            if(message instanceof Integer){
                res = (Integer) message;
                break;
            }
            this.notify();
        }
        return res;
    }

    public synchronized Boolean consumeBool(){
        boolean res;
        while (true){
            if(!full) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            full = false;
            if(message instanceof Boolean){
                res = (Boolean) message;
                break;
            }
            this.notify();
        }
        return res;
    }

}
