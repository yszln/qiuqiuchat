package com.yszln.qiuqiu.entity;

import lombok.Data;

@Data
public class Login {
    private long memberId;
    private String token;

    public Login() {
    }

    public Login(long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }

}
