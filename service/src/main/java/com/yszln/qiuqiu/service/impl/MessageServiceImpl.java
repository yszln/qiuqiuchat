package com.yszln.qiuqiu.service.impl;

import com.yszln.qiuqiu.entity.Login;
import com.yszln.qiuqiu.entity.Message;
import com.yszln.qiuqiu.entity.SendMessageBean;
import com.yszln.qiuqiu.mapper.LoginMapper;
import com.yszln.qiuqiu.mapper.MessageMapper;
import com.yszln.qiuqiu.service.MessageService;
import com.yszln.qiuqiu.socket.SocketUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    MessageMapper messageMapper;

    @Resource
    LoginMapper loginMapper;

    @Override
    public void sendMessage(SendMessageBean sendMessageBean, Long sourceId) {
        if(null==sendMessageBean){
            return;
        }
        //插入消息
        Message message = sendMessageBean.toMessage();
        message.setSourceId(sourceId);
        messageMapper.insert(message);
        //通知用户
        Login login = loginMapper.findById(sendMessageBean.getReceiverId());
        SocketUtils.sendMessage(login.getToken(),message);
    }
}
