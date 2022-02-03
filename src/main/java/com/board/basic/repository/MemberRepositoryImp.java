package com.board.basic.repository;

import com.board.basic.domain.Member;
import com.board.basic.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImp implements MemberRepository{

    private final MemberMapper mapper;

    @Override
    public Member select(String id) {
        return mapper.select(id);
    }

    @Override
    public void insert(Member member) {
        mapper.insert(member);
    }

    @Override
    public void update(Member member) {
        mapper.update(member);
    }

    @Override
    public void delete(String id) {
        mapper.delete(id);
    }

    @Override
    public int checkId(String id) {
        return mapper.chkId(id);
    }
}
