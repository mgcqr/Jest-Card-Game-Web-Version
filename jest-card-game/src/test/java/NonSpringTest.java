import com.mgcqr.jest.entity.UserEntity;
import com.mgcqr.jest.enumeration.GameState;
import com.mgcqr.jest.util.Base64Util;
import com.mgcqr.jest.util.KeyUtil;
import com.mgcqr.jest.util.RSAUtil;
import org.junit.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;


public class NonSpringTest {
    @Test
    public void test(){

        System.out.println(GameState.Running.toString());
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
