package com.yszln.qiuqiu.service;

import com.yszln.qiuqiu.entity.Member;
import com.yszln.qiuqiu.entity.SendMessageBean;


public interface MessageService {
    void sendMessage(SendMessageBean sendMessageBean, Member id);
}
