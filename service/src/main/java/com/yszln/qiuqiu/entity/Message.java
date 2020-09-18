package com.yszln.qiuqiu.entity;

import lombok.Data;

@Data
public class Message {
    private long id;
    private String content;
    private String url;
    private int type;
    private long sourceId;
    private String sourceName;
    private String sourceAvatar;
    private long receiveId;
    private String receiveName;
    private String receiveAvatar;
    private long time;

}
