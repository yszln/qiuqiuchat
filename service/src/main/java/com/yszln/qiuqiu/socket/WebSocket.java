package com.yszln.qiuqiu.socket;


import com.alibaba.fastjson.JSON;
import com.yszln.qiuqiu.entity.BaseBean;
import com.yszln.qiuqiu.entity.Login;
import com.yszln.qiuqiu.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import static com.yszln.qiuqiu.socket.SocketUtils.webSocketSet;

//@Slf4j
@ServerEndpoint("/websocket/{token}")
@Component
public class WebSocket {

    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    public Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    public String token;


    private static LoginService loginService;


    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "token") String token) {
        this.session = session;
        this.token = token;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        Login byToken = loginService.findByToken(token);
        if (null != byToken) {
            //链接
            webSocketSet.put(token, this);
            System.out.println("[WebSocket] 连接成功，当前连接人数为：" + webSocketSet.size());
        } else {
            try {
                session.getBasicRemote().sendText(JSON.toJSONString(new BaseBean(-1, "请登录", null)));
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @OnClose
    public void OnClose() {
        webSocketSet.remove(this.token);
        System.out.println("[WebSocket] 退出成功，当前连接人数为：={}" + webSocketSet.size());
    }

    @OnMessage
    public void OnMessage(String message) {

        System.out.println("[WebSocket] 收到消息：" + message + ",session:" + session.getId() + ",address:" + this);
        //判断是否需要指定发送，具体规则自定义
        if (message.indexOf("TOUSER") == 0) {
            String name = message.substring(message.indexOf("TOUSER") + 6, message.indexOf(";"));
            AppointSending(name, message.substring(message.indexOf(";") + 1, message.length()));
        } else {
            GroupSending(message);
        }

    }

    /**
     * 群发
     *
     * @param message
     */
    public void GroupSending(String message) {
        for (WebSocket socket : webSocketSet.values()) {
            try {
                socket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 指定发送
     *
     * @param name
     * @param message
     */
    public void AppointSending(String name, String message) {
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}