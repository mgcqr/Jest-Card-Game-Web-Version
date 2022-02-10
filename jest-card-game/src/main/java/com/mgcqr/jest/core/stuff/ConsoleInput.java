package com.mgcqr.jest.core.stuff;

import java.util.Scanner;

public class ConsoleInput implements Runnable {
    private Thread t = null;
    private MailBox mailBox;
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
                    mailBox.produce(a);
                }catch(Exception e) {
                    boolean faceUp = Boolean.parseBoolean(str);
                    mailBox.produce(faceUp);
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
