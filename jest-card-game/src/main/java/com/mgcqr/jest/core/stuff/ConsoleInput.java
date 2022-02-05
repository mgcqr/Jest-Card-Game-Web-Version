package com.mgcqr.jest.core.stuff;

import java.util.Scanner;

public class ConsoleInput implements Runnable {
    private Thread t = null;
    public void run() {
        Scanner scan = Table.scan;
        String str;
        while(true) {
            if(scan.hasNext()) {
                str = scan.next();
                try {
                    int a = Integer.parseInt(str);
                    MailBox.produce(a);
                }catch(Exception e) {
                    boolean faceUp = Boolean.parseBoolean(str);
                    MailBox.produce(faceUp);
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
