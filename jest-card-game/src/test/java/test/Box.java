package test;

import com.mgcqr.jest.core.dto.MailBoxAbsDto;

public class Box {
    private MailBoxAbsDto message;
    private boolean full = false;

    public synchronized void produce(MailBoxAbsDto msg){
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

    public synchronized<T extends MailBoxAbsDto> T consume(Class<T> type){
        T res;
        while (true){
            if(!full) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            full = false;
            if(type.isInstance(message)){
                res = (T) message;
                break;
            }
            this.notify();
        }
        return res;
    }
}
