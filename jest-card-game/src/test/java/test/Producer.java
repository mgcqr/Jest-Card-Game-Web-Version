package test;

import com.mgcqr.jest.core.dto.MailBoxAbsDto;

public class Producer {
    private Box box;
    public Producer(Box b){box = b;}
    public void produce(MailBoxAbsDto msg){
        box.produce(msg);
    }
}
