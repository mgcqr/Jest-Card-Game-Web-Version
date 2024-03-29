import com.fasterxml.jackson.core.JsonProcessingException;
import com.mgcqr.jest.dto.ws.GameResultResDto;
import com.mgcqr.jest.dto.ws.InfoBroadcastDto;
import com.mgcqr.jest.dto.ws.MakeOfferResDto;
import com.mgcqr.jest.entity.UserEntity;
import com.mgcqr.jest.model.RuntimeUserInfo;
import com.mgcqr.jest.util.JsonUtil;
import org.junit.Test;
import test.Box;
import test.Consumer;
import test.Producer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class NonSpringTest {
    @Test
    public void test(){
        GameResultResDto dto = new GameResultResDto();
        try {
            System.out.println(JsonUtil.toJsonString(dto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

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
