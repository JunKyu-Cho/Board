package com.board.basic.repository;

import com.board.basic.domain.Member;

public interface MemberRepository {
    Member select(String id);
    void insert(Member member);
    void update(Member member);
    void delete(String id);
    int checkId(String id);
}
