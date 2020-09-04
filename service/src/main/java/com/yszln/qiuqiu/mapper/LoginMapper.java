package com.yszln.qiuqiu.mapper;

import com.yszln.qiuqiu.entity.Login;

public interface LoginMapper {
    Login findById(long memberId);

    Login findByToken(String token);

    int insert(Login login);

    int delete(long memberId);
}
