package com.yszln.qiuqiu.controller;

import com.yszln.qiuqiu.entity.BaseBean;
import com.yszln.qiuqiu.entity.Login;
import com.yszln.qiuqiu.entity.LoginSuccessBean;
import com.yszln.qiuqiu.entity.Member;
import com.yszln.qiuqiu.service.LoginService;
import com.yszln.qiuqiu.service.MemberService;
import com.yszln.qiuqiu.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @RequestMapping("/findAll")
    public BaseBean findAll() {
        return new BaseBean<>(200, "success", memberService.findAll());
    }

    @PostMapping("/register")
    public BaseBean register(@RequestParam("username") String username,
                             @RequestParam("password") String password) {

        int register = memberService.register(username, password);
        if (register > 0) {
            //登陆成功
            return memberService.login(username, password);
        }
        return new BaseBean<>(500, "注册失败", null);
    }

    @PostMapping("/login")
    public BaseBean login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        return memberService.login(username, password);
    }
}
