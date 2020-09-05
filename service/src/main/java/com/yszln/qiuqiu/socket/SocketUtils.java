package com.yszln.qiuqiu.socket;

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
