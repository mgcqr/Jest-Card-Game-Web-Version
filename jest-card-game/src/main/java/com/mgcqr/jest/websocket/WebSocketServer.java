package com.mgcqr.jest.websocket;

import com.mgcqr.jest.core.CoreInterface;
import com.mgcqr.jest.dto.GameInstructionDto;
import com.mgcqr.jest.enumeration.InstructionType;
import com.mgcqr.jest.model.InstructionInfo;
import com.mgcqr.jest.repository.RedisCacheRepository;
import com.mgcqr.jest.service.UserService;
import com.mgcqr.jest.util.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@ServerEndpoint("/webSocket")
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static final AtomicInteger onlineNum = new AtomicInteger();

    //userId to session
    private static final ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();
    //userId to game
    private static final ConcurrentHashMap<String, CoreInterface> gameCorePool = new ConcurrentHashMap<>();

    /**
     * {@code
     * @Autowired
     * private static UserService userService;
     * }
     * 这样注入的bean不能被onError onMessage onClose onOpen访问
     * 只有sendMessage这样不带websocket注解的方法可以访问
     */
    private static UserService userService;
    @Autowired
    public void setUserService(UserService userService){
        WebSocketServer.userService = userService;
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
        Session session = sessionPool.get(userName);
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
    public void onClose(Session session){
        subOnlineCount();
        System.out.println( "断开webSocket连接！当前人数为" + onlineNum);
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(Session session, String message) throws IOException{
        GameInstructionDto dto;
        try{
            dto = JsonUtil.toObject(message, GameInstructionDto.class);
        }catch (Exception e){
            //入参格式不对,关闭连接
            System.out.println("wrong input format");
            session.close();
            return;
        }

        if(dto.getType() != null){
            String token = dto.getToken();
            String userId = userService.getId(token);
            if(userId == null){
                System.out.println("token missing or wrong. Closing");
                session.close();
                return;
            }
            System.out.println("receive message from websocket");
            System.out.println("user id: " + userId);
            if(dto.getType() == InstructionType.Initial){ //first message only
                sessionPool.put(userId, session);
            }else {
                InstructionInfo instruction = new InstructionInfo();
                BeanUtils.copyProperties(dto, instruction);
                instruction.setUserId(userId);
                CoreInterface coreInterface = gameCorePool.get(userId);
                if(coreInterface != null){
                    coreInterface.produce(instruction);
                }
            }
        }else {
            System.out.println("wrong input format");
            session.close();
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public void registerGame(List<String> userIds, CoreInterface coreInterface){
        for(String id : userIds){
            gameCorePool.put(id, coreInterface);
        }
    }

    private static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    private static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

}
