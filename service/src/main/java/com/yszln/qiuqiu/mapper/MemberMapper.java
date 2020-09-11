package com.yszln.qiuqiu.mapper;

import com.yszln.qiuqiu.entity.Login;
import com.yszln.qiuqiu.entity.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMapper {
    List<Member> findAll();

    List<Member> findMemberByUserName(String username);

    Member login(@Param("username") String username,
                       @Param("password") String password);

    int register(@Param("username") String username,
                       @Param("password") String password);

    int findMemberCount(String usesrname);

    Member findMemberByToken(String token);

    Member findMemberById(long id);
}
