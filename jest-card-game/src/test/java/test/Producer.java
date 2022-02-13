package test;

import test.Box;

public class Producer {
    private Box box;
    public Producer(Box b){box = b;}
    public void produce(String msg){
        box.setMessage(msg);
    }
}
