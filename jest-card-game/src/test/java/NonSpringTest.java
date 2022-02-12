import com.mgcqr.jest.entity.UserEntity;
import com.mgcqr.jest.enumeration.GameState;
import com.mgcqr.jest.util.Base64Util;
import com.mgcqr.jest.util.KeyUtil;
import com.mgcqr.jest.util.RSAUtil;
import org.junit.Test;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.concurrent.ConcurrentHashMap;


public class NonSpringTest {
    @Test
    public void test(){

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("a", 2);


        System.out.println("test");
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
