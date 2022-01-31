import com.mgcqr.jest.entity.User;
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

        System.out.println(KeyUtil.getKey());
    }

    @Test
    public void testRSA(){
        try {
            //===============生成公钥和私钥，公钥传给客户端，私钥服务端保留==================
            //生成RSA公钥和私钥，并Base64编码
            KeyPair keyPair = RSAUtil.getKeyPair();
            String publicKeyStr = RSAUtil.getPublicKey(keyPair);
            String privateKeyStr = RSAUtil.getPrivateKey(keyPair);
            System.out.println("RSA公钥Base64编码:\n" + publicKeyStr);
            System.out.println("RSA私钥Base64编码:\n" + privateKeyStr);

            //=================客户端=================
            //hello, i am infi, good night!加密
            String message = "hello, i am infi, good night!";
            //将Base64编码后的公钥转换成PublicKey对象
            PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
            //用公钥加密
            byte[] publicEncrypt = RSAUtil.publicEncrypt(message.getBytes(), publicKey);
            //加密后的内容Base64编码
            String byte2Base64 = Base64Util.byte2String(publicEncrypt);
            System.out.println("公钥加密并Base64编码的结果：\n" + byte2Base64);


            //##############    网络上传输的内容有Base64编码后的公钥 和 Base64编码后的公钥加密的内容     #################



            //===================服务端================
            //将Base64编码后的私钥转换成PrivateKey对象
            PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
            //加密后的内容Base64解码
            byte[] base642Byte = Base64Util.String2Byte(byte2Base64);
            //用私钥解密
            byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
            //解密后的明文
            System.out.println("解密后的明文: \n" + new String(privateDecrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClass(){
        User u = new User();
        u.setUserName("tom");
        u.setId("123");
        u.setPassWord("qwer");

        String name = u.getClass().getName();
        System.out.println(name);
    }
}
