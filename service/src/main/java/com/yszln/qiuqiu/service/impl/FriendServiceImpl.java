package com.yszln.qiuqiu.service.impl;

import com.yszln.qiuqiu.entity.Member;
import com.yszln.qiuqiu.mapper.FriendMapper;
import com.yszln.qiuqiu.service.FriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Resource
    FriendMapper friendMapper;

    @Override
    public List<Member> findFriendByMemberId(long id) {
        return friendMapper.findByMemberId(id);
    }
}
