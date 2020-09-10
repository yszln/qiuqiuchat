package com.yszln.qiuqiu.service.impl;

import com.yszln.qiuqiu.bean.BaseBean;
import com.yszln.qiuqiu.bean.ErrorBean;
import com.yszln.qiuqiu.bean.SuccessBean;
import com.yszln.qiuqiu.entity.*;
import com.yszln.qiuqiu.mapper.LoginMapper;
import com.yszln.qiuqiu.mapper.MemberMapper;
import com.yszln.qiuqiu.service.MemberService;
import com.yszln.qiuqiu.utils.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private LoginMapper loginMapper;

    @Override
    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    @Override
    public int register(String username, String password) {
        int memberCount = memberMapper.findMemberCount(username);
        if(memberCount>0){
            return -1;
        }
        return memberMapper.register(username, MD5Utils.stringToMD5(password));
    }

    @Override
    public BaseBean login(String username, String password) {
        Member loginUser = memberMapper.login(username, MD5Utils.stringToMD5(password));

        if (loginUser != null) {
            String token = MD5Utils.stringToMD5(System.currentTimeMillis() + loginUser.getUsername());

            LoginSuccessBean loginSuccessBean = new LoginSuccessBean(loginUser, token);
            loginUser.setPassword(null);
            int insert = loginMapper.insert(new Login(loginUser.getId(), token));
            if (insert > 0) {
                return new SuccessBean<>( "success", loginSuccessBean);
            }
            return new ErrorBean<>( "登陆异常", null);
        }
        return new ErrorBean<>( "账号或者密码错误", null);

    }

    @Override
    public Member findLoginMember(String token) {
        return memberMapper.findMemberByToken(token);
    }
}
