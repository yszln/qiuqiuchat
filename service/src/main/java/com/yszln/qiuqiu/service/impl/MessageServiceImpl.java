package com.yszln.qiuqiu.service.impl;

import com.yszln.qiuqiu.entity.Login;
import com.yszln.qiuqiu.entity.Member;
import com.yszln.qiuqiu.entity.Message;
import com.yszln.qiuqiu.entity.SendMessageBean;
import com.yszln.qiuqiu.mapper.LoginMapper;
import com.yszln.qiuqiu.mapper.MemberMapper;
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

    @Resource
    MemberMapper memberMapper;

    @Override
    public void sendMessage(SendMessageBean sendMessageBean, Member sendMember) {
        if (null == sendMessageBean) {
            return;
        }
        //插入消息
        Message message = sendMessageBean.toMessage();

        int messageId = messageMapper.insert(message);
        //通知用户
        message.setId(messageId);
        message.setSourceId(sendMember.getId());
        message.setSourceName(sendMember.getUsername());
        message.setSourceAvatar(sendMember.getAvatar());
        Login login = loginMapper.findById(sendMessageBean.getReceiverId());
        Member receiverMember = memberMapper.findMemberById(login.getMemberId());
        message.setReceiveAvatar(receiverMember.getAvatar());
        message.setReceiveName(receiverMember.getUsername());
        SocketUtils.sendMessage(login.getToken(), message);
    }
}
