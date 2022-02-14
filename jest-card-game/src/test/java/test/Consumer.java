package test;

import com.mgcqr.jest.core.dto.MakeOfferInstructionDto;
import com.mgcqr.jest.core.dto.TakeCardInstructionDto;

public class Consumer implements Runnable {
    private Box box;
    public Consumer(Box b){
        box = b;
    }

    public void run(){
        while (true) {
            System.out.println(box.consume(MakeOfferInstructionDto.class));
            System.out.println(box.consume(TakeCardInstructionDto.class));
        }
    }
}
