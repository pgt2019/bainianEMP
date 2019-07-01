package com.ruoyi.WebSocket;

import io.netty.handler.codec.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

@ServerEndpoint
@Component
public class WebSocket {

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers){
        System.out.println("new connection");
    }

    @OnClose
    public void onClose(Session session){
        System.out.println("one connection close");
    }

    @OnError
    public void onError(Session session,Throwable throwable){
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session,String message){
        System.out.println(message);
        session.sendText("111111111");
    }
}
