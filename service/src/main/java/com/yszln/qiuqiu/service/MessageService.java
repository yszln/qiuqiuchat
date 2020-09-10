package com.yszln.qiuqiu.service;

import com.yszln.qiuqiu.entity.SendMessageBean;


public interface MessageService {
    void sendMessage(SendMessageBean sendMessageBean, Long id);
}
