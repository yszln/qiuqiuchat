package com.yszln.qiuqiu.service.impl;

import com.yszln.qiuqiu.entity.BaseBean;
import com.yszln.qiuqiu.entity.Login;
import com.yszln.qiuqiu.entity.LoginSuccessBean;
import com.yszln.qiuqiu.entity.Member;
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
                return new BaseBean<>(200, "success", loginSuccessBean);
            }
            return new BaseBean<>(500, "登陆异常", null);
        }
        return new BaseBean<>(500, "账号或者密码错误", null);

    }
}
