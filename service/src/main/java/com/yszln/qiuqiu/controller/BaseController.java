package com.yszln.qiuqiu.controller;

import com.yszln.qiuqiu.entity.Member;
import com.yszln.qiuqiu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Autowired
    HttpServletRequest request;

    @Autowired
    MemberService memberService;

    public Member getLoginMember() {
        String token = request.getHeader("token");
        return memberService.findLoginMember(token);
    }

    public long getLoginId() {
        return getLoginMember().getId();
    }

}
