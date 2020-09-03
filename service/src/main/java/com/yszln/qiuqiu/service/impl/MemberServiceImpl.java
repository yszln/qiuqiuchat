package com.yszln.qiuqiu.service.impl;

import com.yszln.qiuqiu.entity.Member;
import com.yszln.qiuqiu.mapper.MemberMapper;
import com.yszln.qiuqiu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<Member> findAll() {
        return memberMapper.findAll();
    }
}
