package com.yszln.qiuqiu.entity;


public class Login {
    private long memberId;
    private String token;

    public Login() {
    }

    public Login(long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
