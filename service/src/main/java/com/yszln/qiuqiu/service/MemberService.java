package com.yszln.qiuqiu.service;

import com.yszln.qiuqiu.bean.BaseBean;
import com.yszln.qiuqiu.entity.Member;

import java.util.List;


public interface MemberService {
    List<Member> findAll();

    int register(String username, String password);

    BaseBean login(String username, String password);

    Member findLoginMember(String token);
}
