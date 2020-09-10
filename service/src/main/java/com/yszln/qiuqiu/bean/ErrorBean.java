package com.yszln.qiuqiu.bean;

public class ErrorBean<T> extends BaseBean {
    public ErrorBean(String message, T data) {
        super(500, message, data);
    }

    public ErrorBean(String message) {
        this(message, null);
    }
}
