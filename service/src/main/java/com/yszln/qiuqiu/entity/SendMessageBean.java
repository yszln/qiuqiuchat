package com.yszln.qiuqiu.entity;

import lombok.Data;

@Data
public class SendMessageBean {
    private long receiverId;
    private String content;
    private int type;

    public Message toMessage() {
        Message message = new Message();
        message.setContent(content);
        message.setType(type);
        message.setReceiveId(receiverId);
        return message;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
