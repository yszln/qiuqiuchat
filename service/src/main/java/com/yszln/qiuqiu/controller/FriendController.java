package com.yszln.qiuqiu.controller;

import com.yszln.qiuqiu.bean.BaseBean;
import com.yszln.qiuqiu.bean.ErrorBean;
import com.yszln.qiuqiu.bean.LoginErrorBean;
import com.yszln.qiuqiu.bean.SuccessBean;
import com.yszln.qiuqiu.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
public class FriendController extends BaseController {

    @Autowired
    FriendService friendService;

    @PostMapping("/findAll")
    public BaseBean findFriend() {
        long loginId;
        try {
             loginId = getLoginId();
        }catch (Exception e){
            return new LoginErrorBean("请登录");
        }
        return new SuccessBean(friendService.findFriendByMemberId(loginId));
    }
}
