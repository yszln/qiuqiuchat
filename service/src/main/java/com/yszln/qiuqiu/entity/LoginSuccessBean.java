package com.yszln.qiuqiu.entity;


import lombok.Data;

@Data
public class LoginSuccessBean {

    private Member member;
    private String token;

    public LoginSuccessBean(Member member, String token) {
        this.member = member;
        this.token = token;
    }
}
