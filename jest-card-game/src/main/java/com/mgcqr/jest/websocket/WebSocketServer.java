package com.mgcqr.jest.websocket;

import com.mgcqr.jest.dto.GameInstructionDto;
import com.mgcqr.jest.dto.LoginResDto;
import com.mgcqr.jest.dto.PublicKeyResDto;
import com.mgcqr.jest.interceptor.UserContextHolder;
import com.mgcqr.jest.model.CurrentUserInfo;
import com.mgcqr.jest.model.InstructionInfo;
import com.mgcqr.jest.repository.RedisCacheRepository;
import com.mgcqr.jest.service.impl.BasicServiceImpl;
import com.mgcqr.jest.util.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@ServerEndpoint("/webSocket")
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * {@code
     * @Autowired
     * private RedisCacheRepository redis;
     * }
     * 这样注入的bean不能被onError onMessage onClose onOpen访问
     * 只有sendMessage这样不带websocket注解的方法可以访问
     */
    private static RedisCacheRepository redis;

    @Autowired
    public void setRedisCacheRepository(RedisCacheRepository redis){
        WebSocketServer.redis = redis;
    }

    //发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (this) {
//                System.out.println("发送数据：" + message);
                session.getBasicRemote().sendText(message);
//                session.getBasicRemote().sendObject();
            }
        }
    }
    //给指定用户发送信息
    public void sendInfo(String userName, String message){
        Session session = sessionPools.get(userName);
        try {
            sendMessage(session, message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session){
        addOnlineCount();
        System.out.println("加入webSocket！当前人数为" + onlineNum);
        try {
            sendMessage(session, "欢迎" + session.getId() + "加入连接！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose(){
        subOnlineCount();
        System.out.println( "断开webSocket连接！当前人数为" + onlineNum);
    }



    //收到客户端信息
    @OnMessage
    public void onMessage(Session session, String message) throws IOException{
        System.out.println(message);
        GameInstructionDto dto = JsonUtil.toObject(message, GameInstructionDto.class);
        InstructionInfo instruction = new InstructionInfo();
        BeanUtils.copyProperties(dto, instruction);

        String token = dto.getToken();
        if(StringUtils.hasText(token) && redis.hasKey(token)){
            System.out.println("user logged in on websocket");
            System.out.println("token: " + token);
            redis.expireDefault(token);
            CurrentUserInfo currentUser = redis.getObject(token,CurrentUserInfo.class);
            instruction.setUserId(currentUser.getId());
        }else {
            System.out.println("token missing or wrong. Closing");
            session.close();
        }




        System.out.println(dto.toString());
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

}
