public class Box {
    private String message;
    private boolean full;

    public synchronized void setMessage(String msg){
        message = msg;
        full = true;
    }
    public synchronized String getMessage(){
        full = false;
        return message;
    }
    public synchronized boolean isFull(){
        return full;
    }
}
