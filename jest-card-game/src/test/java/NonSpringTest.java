import com.mgcqr.jest.entity.UserEntity;
import org.junit.Test;
import test.Box;
import test.Consumer;
import test.Producer;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public class NonSpringTest {
    @Test
    public void test(){
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
        map.put("aa","bb");
        String s = map.get("asda");

        System.out.println();

    }

    @Test
    public void testClass(){
        UserEntity u = new UserEntity();
        u.setUserName("tom");
        u.setId("123");
        u.setPassWord("qwer");

        String name = u.getClass().getName();
        System.out.println(name);
    }

    @Test
    public void testSynchronized(){
        Box box = new Box();
        Consumer consumer = new Consumer(box);
        Producer producer = new Producer(box);
        Thread t = new Thread(consumer);
        t.start();
//
//        MailBoxDto dto = new MailBoxDto();
//
//        MakeOfferDto makeOfferDto = new MakeOfferDto();
//        makeOfferDto.setChoice(12);
//
//        TakeCardDto takeCardDto = new TakeCardDto();
//        takeCardDto.setIsFaceUp(true);
//        takeCardDto.setPlayerID(100);
//
//        producer.produce(dto);
//        producer.produce(makeOfferDto);
//        producer.produce(takeCardDto);
//        producer.produce(takeCardDto);
//        producer.produce(makeOfferDto);
//        producer.produce(takeCardDto);
//        producer.produce(makeOfferDto);


        System.out.println("test");
    }
}
