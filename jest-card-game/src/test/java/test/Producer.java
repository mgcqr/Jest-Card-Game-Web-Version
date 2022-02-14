package test;

import com.mgcqr.jest.core.dto.MailBoxDto;
import test.Box;

public class Producer {
    private Box box;
    public Producer(Box b){box = b;}
    public void produce(MailBoxDto msg){
        box.produce(msg);
    }
}
