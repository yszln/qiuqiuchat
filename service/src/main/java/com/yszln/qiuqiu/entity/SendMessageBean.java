package com.yszln.qiuqiu.entity;

import lombok.Data;

@Data
public class SendMessageBean {
    private long receiverId;
    private String content;
    private String url;
    private int type;

    public Message toMessage() {
        Message message = new Message();
        message.setContent(content);
        message.setType(type);
        message.setUrl(url);
        message.setReceiveId(receiverId);
        return message;
    }


}
