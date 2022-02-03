package com.board.basic.service;

import com.board.basic.domain.Member;

public interface MemberService {
    Member select(String id);
    void insert(Member member);
    void update(Member member);
    void delete(String id);

    int checkId(String id);
}
