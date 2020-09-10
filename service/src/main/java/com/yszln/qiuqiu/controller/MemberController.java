package com.yszln.qiuqiu.controller;

import com.yszln.qiuqiu.bean.BaseBean;
import com.yszln.qiuqiu.bean.ErrorBean;
import com.yszln.qiuqiu.bean.SuccessBean;
import com.yszln.qiuqiu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @RequestMapping("/findAll")
    public BaseBean findAll() {
        return new SuccessBean<>( "success", memberService.findAll());
    }

    @PostMapping("/register")
    public BaseBean register(@RequestParam("username") String username,
                             @RequestParam("password") String password) {

        int register = memberService.register(username, password);
        if (register > 0) {
            //登陆成功
            return memberService.login(username, password);
        }else if(register==-1){
            return new ErrorBean<>( "该用户名已存在", null);
        }
        return new ErrorBean<>( "注册失败", null);
    }

    @PostMapping("/login")
    public BaseBean login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        return memberService.login(username, password);
    }
}
