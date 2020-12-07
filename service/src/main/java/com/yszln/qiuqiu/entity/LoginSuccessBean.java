package com.yszln.qiuqiu.entity;



public class LoginSuccessBean {

    private Member member;
    private String token;

    public LoginSuccessBean(Member member, String token) {
        this.member = member;
        this.token = token;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
