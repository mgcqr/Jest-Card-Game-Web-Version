public class Consumer implements Runnable {
    private Box box;
    public Consumer(Box b){box = b;}
    public void run(){
        while (true) {
            System.out.println(box.getMessage());
        }
    }
}
