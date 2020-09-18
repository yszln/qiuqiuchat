package com.yszln.qiuqiu.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Member implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String avatar;
    private int status;

}
