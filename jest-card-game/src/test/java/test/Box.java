package test;

public class Box {
    private String message;
    private boolean full;

    public synchronized void setMessage(String msg){
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
    public synchronized String getMessage(){
        if(! full) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        full = false;
        String res = message;
        this.notify();
        return res;
    }
}
