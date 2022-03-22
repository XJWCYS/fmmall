package com.cys.fmmall.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

//创建websocket服务器
@Component
@ServerEndpoint("/webSocket/{oid}")
public class WebSocketServer {
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();
    /**前端发送请求建⽴websocket连接，就会执⾏@OnOpen⽅法**/
    @OnOpen
    public void open(@PathParam("oid") String orderId,Session session){
        sessionMap.put(orderId,session);
    }
    /**前端关闭⻚⾯或者主动关闭websocket连接，都会执⾏close**/
    @OnClose
    public void close(@PathParam("oid") String orderId){
        sessionMap.remove(orderId);
    }
    //向前端发送信息
    public static void sendMsg(String orderId,String msg){
        try {
            Session session = sessionMap.get(orderId);
            session.getBasicRemote().sendText(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
