package com.yszln.qiuqiu.service.impl;

import com.yszln.qiuqiu.entity.Login;
import com.yszln.qiuqiu.mapper.LoginMapper;
import com.yszln.qiuqiu.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginMapper loginMapper;

    @Override
    public Login findById(long memberId) {
        return loginMapper.findById(memberId);
    }

    @Override
    public Login findByToken(String token) {
        return loginMapper.findByToken(token);
    }

    @Override
    public int insert(Login login) {
        return loginMapper.insert(login);
    }

    @Override
    public int delete(long memberId) {
        return loginMapper.delete(memberId);
    }
}
