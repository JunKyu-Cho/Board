package com.board.basic.mapper;

import com.board.basic.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    Member select(String id);
    void insert(Member member);
    void update(Member member);
    void delete(String id);

    int chkId(String id);
}
