package com.yszln.qiuqiu.socket;

import com.alibaba.fastjson.JSON;
import com.yszln.qiuqiu.entity.Message;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class SocketUtils {
    /**
     * 用于存所有的连接服务的客户端
     */
    public static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();

    /**
     * 群发
     *
     * @param message
     */
    public static void GroupSending(String message) {
        for (WebSocket socket : webSocketSet.values()) {
            try {
                socket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendMessage(String receiverToken, Message message) {
        for (String key : webSocketSet.keySet()) {
            if (key.equals(receiverToken)) {
                try {
                    webSocketSet.get(key).session.getBasicRemote().sendText(JSON.toJSONString(message));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * 指定发送
     *
     * @param token
     * @param message
     */
    public void AppointSending(String token, String message) {
        try {
            webSocketSet.get(token).session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
