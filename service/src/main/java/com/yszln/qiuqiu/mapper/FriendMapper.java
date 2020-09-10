package com.yszln.qiuqiu.mapper;

import com.yszln.qiuqiu.entity.Member;

import java.util.List;

public interface FriendMapper {
    List<Member> findByMemberId(long id);
}
