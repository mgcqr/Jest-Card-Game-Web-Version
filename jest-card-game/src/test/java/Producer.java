public class Producer {
    private Box box;
    public Producer(Box b){box = b;}
    public void produce(String msg){
        synchronized (box){
            if(box.isFull()){
                try {
                    box.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            box.setMessage(msg);
            box.notify();
        }
    }
}
