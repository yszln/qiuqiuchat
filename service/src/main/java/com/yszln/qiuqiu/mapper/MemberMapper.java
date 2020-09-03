package com.yszln.qiuqiu.mapper;

import com.yszln.qiuqiu.entity.Member;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberMapper {
    @Select("select * from tb_member")
    List<Member> findAll();
}
