package com.yszln.qiuqiu.controller;

import com.yszln.qiuqiu.entity.BaseBean;
import com.yszln.qiuqiu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/findAll")
    public BaseBean findAll() {
        return new BaseBean(0,"success",memberService.findAll());
    }

}
