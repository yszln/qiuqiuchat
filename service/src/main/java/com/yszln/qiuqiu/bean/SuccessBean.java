package com.yszln.qiuqiu.bean;

public class SuccessBean<T> extends BaseBean {

    public SuccessBean(String message, T data) {
        super(200, message, data);
    }

    public SuccessBean(Object data) {
        super(200, "success", data);
    }
}
