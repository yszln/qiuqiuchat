package com.yszln.qiuqiu.service;

import com.yszln.qiuqiu.entity.Member;

import java.util.List;

public interface FriendService {
    List<Member> findFriendByMemberId(long id);
}
