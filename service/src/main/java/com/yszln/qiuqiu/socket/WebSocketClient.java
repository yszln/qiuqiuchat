package com.yszln.qiuqiu.socket;


import com.alibaba.fastjson.JSON;
import com.yszln.qiuqiu.bean.BaseBean;
import com.yszln.qiuqiu.bean.ErrorBean;
import com.yszln.qiuqiu.entity.Member;
import com.yszln.qiuqiu.entity.SendMessageBean;
import com.yszln.qiuqiu.service.MemberService;
import com.yszln.qiuqiu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import static com.yszln.qiuqiu.socket.SocketUtils.webSocketSet;

//@Slf4j
@ServerEndpoint("/websocket/{token}")
@Component
public class WebSocketClient {

    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    public Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    public String token;

    public Member loginMember;


    private static MemberService memberService;

    private static MessageService messageService;


    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "token") String token) {
        this.session = session;
        this.token = token;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        loginMember = memberService.findLoginMember(token);
        if (null != loginMember) {
            //链接
            webSocketSet.put(token, this);
            System.out.println("[WebSocket] 连接成功，当前连接人数为：" + webSocketSet.size());
        } else {

            closeSocket();

        }

    }

    private void closeSocket() {
        try {
            session.getBasicRemote().sendText(JSON.toJSONString(new BaseBean(501,"请登录", null)));
//            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @OnClose
    public void OnClose() {
        webSocketSet.remove(this.token);
        System.out.println("[WebSocket] 退出成功，当前连接人数为：={}" + webSocketSet.size());
    }

    @OnMessage
    public void OnMessage(String message) {
        if (null == message || "".equals(message)) {
            System.out.println("[WebSocket] 心跳检测：" + loginMember.getId());
            return;
        }
        if (null == loginMember) {
            closeSocket();
            return;
        }
        try {
            SendMessageBean sendMessageBean = JSON.parseObject(message, SendMessageBean.class);
            messageService.sendMessage(sendMessageBean, loginMember);
        } catch (Exception e) {
            System.out.println("发送失败：" + e.getMessage());
        }

        System.out.println("[WebSocket] 收到消息：" + message + ",session:" + session.getId());
        //判断是否需要指定发送，具体规则自定义

    }

    /*@OnError
    public void OnError(Session session, Throwable throwable) {
        System.out.println(JSON.toJSONString(throwable));
    }*/


    @Autowired
    public void setMemberService(MemberService service) {
        this.memberService = service;
    }

    @Autowired
    public void setMessageService(MessageService service) {
        this.messageService = service;
    }
}