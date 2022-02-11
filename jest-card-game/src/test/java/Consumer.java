public class Consumer implements Runnable {
    private Box box;
    public Consumer(Box b){box = b;}
    public void run(){
        while (true) {
            synchronized (box){
                if(!box.isFull()){
                    try {
                        box.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(box.getMessage());
                box.notify();
            }
        }
    }
}
