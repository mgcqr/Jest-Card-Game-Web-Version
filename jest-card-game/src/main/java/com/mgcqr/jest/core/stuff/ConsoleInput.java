package com.mgcqr.jest.core.stuff;

import java.util.Scanner;

public class ConsoleInput implements Runnable {
    private Thread t = null;
    private final MailBox mailBox;
    public ConsoleInput(MailBox mailBox){
        this.mailBox = mailBox;
    }
    public void run() {
        Scanner scan = new Scanner(System.in);
        String str;
        while(true) {
            if(scan.hasNext()) {
                str = scan.next();
                try {
                    int a = Integer.parseInt(str);
                    synchronized (mailBox){
                        if(mailBox.isFull()){
                            try {
                                mailBox.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        mailBox.produce(a);
                        mailBox.notify();
                    }
                }catch(Exception e) {
                    boolean faceUp = Boolean.parseBoolean(str);
                    synchronized (mailBox){
                        if(mailBox.isFull()){
                            try {
                                mailBox.wait();
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                        }
                        mailBox.produce(faceUp);
                        mailBox.notify();
                    }
                }

            }
        }
    }
    public void start() {
        if(t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
