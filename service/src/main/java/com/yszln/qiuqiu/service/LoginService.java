package com.yszln.qiuqiu.service;

import com.yszln.qiuqiu.entity.Login;

public interface LoginService {
    Login findById(long memberId);

    Login findByToken(String token);

    int insert(Login login);

    int delete(long memberId);
}
