package com.yszln.qiuqiu.socket;

import java.util.concurrent.ConcurrentHashMap;

public class SocketUtils {
    /**
     * 用于存所有的连接服务的客户端
     */
    public static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();

}
