package com.yszln.qiuqiu.bean;

public class LoginErrorBean<T> extends BaseBean {
    public LoginErrorBean(String message, T data) {
        super(501, message, data);
    }

    public LoginErrorBean(String message) {
        this(message, null);
    }
}
