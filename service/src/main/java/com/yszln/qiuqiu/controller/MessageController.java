package com.yszln.qiuqiu.controller;

import com.yszln.qiuqiu.socket.SocketUtils;
import org.junit.Test;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @PostMapping(value = "/pushMessage")
    public void pushMessage(@RequestParam("message") String message) {
        SocketUtils.GroupSending(message);
    }



}
