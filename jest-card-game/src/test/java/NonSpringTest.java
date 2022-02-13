import com.fasterxml.jackson.core.JsonProcessingException;
import com.mgcqr.jest.core.dto.MailBoxDto;
import com.mgcqr.jest.core.stuff.MailBox;
import com.mgcqr.jest.dto.GameInstructionDto;
import com.mgcqr.jest.dto.LoginDto;
import com.mgcqr.jest.dto.LoginResDto;
import com.mgcqr.jest.entity.UserEntity;
import com.mgcqr.jest.model.InstructionInfo;
import com.mgcqr.jest.util.JsonUtil;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.junit.Test;
import test.Box;
import test.Consumer;
import test.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class NonSpringTest {
    @Test
    public void test(){
        ArrayList<String> list = new ArrayList<>();
        list.add("asda");
        list.add("qweqwe");
        list.add("zxcz");
        String[] array = list.toArray(new String[]{});
        System.out.println(array);

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
        for(int i = 0; i < 10; i++){
            producer.produce("message" + i);
        }
    }
}
