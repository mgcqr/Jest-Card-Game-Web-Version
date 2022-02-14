package com.mgcqr.jest.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mgcqr.jest.core.CoreInterface;
import com.mgcqr.jest.dto.ws.GameInstructionDto;
import com.mgcqr.jest.dto.ws.GameResponseAbsDto;
import com.mgcqr.jest.enumeration.InstructionType;
import com.mgcqr.jest.service.UserService;
import com.mgcqr.jest.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@ServerEndpoint("/webSocket")
public class WebSocketRouter {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static final AtomicInteger onlineNum = new AtomicInteger();

    //userId to session
    private static final ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();
    //userId to game core
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
        WebSocketRouter.userService = userService;
    }


    //发送消息
    private void sendMessage(Session session, String message) {
        if(session != null){
//            synchronized (this) {
//                session.getBasicRemote().sendText(message);
//            }
            session.getAsyncRemote().sendText(message);
        }
    }
    //给指定用户发送信息
    public void sendMessage(String userId, GameResponseAbsDto responseDto){
        Session session = sessionPool.get(userId);
        try {
            sendMessage(session, JsonUtil.toJsonString(responseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void multicast(List<String> userIds, GameResponseAbsDto responseDto){
        for(String id : userIds){
            sendMessage(id, responseDto);
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session){
        addOnlineCount();
        System.out.println("加入webSocket！当前人数为" + onlineNum);
        sendMessage(session, "欢迎" + session.getId() + "加入连接！");
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
                dto.setUserId(userId);
                CoreInterface coreInterface = gameCorePool.get(userId);
                if(coreInterface != null){
                    coreInterface.produce(dto);
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

    public boolean registerGame(List<String> userIds, CoreInterface coreInterface){
        for(String id : userIds){
            gameCorePool.put(id, coreInterface);
        }

        for(int i = 0; i < 10; i++){
            if(sessionPool.keySet().containsAll(userIds))
                return true;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void cancelGame(List<String> userIds){
        Set<String> idSet = new HashSet<>(userIds);
        for(String key : sessionPool.keySet()){
            if(idSet.contains(key))
                sessionPool.remove(key);
        }
        for(String key : gameCorePool.keySet()){
            if(idSet.contains(key))
                gameCorePool.remove(key);
        }
    }

    private static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    private static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

}
