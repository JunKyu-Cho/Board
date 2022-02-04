package com.board.basic.repository;

import com.board.basic.domain.Member;
import com.board.basic.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImp implements MemberRepository{

    /*
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
    */

    private final SqlSession session;

    @Override
    public Member select(String id) {
        return session.selectOne("MemberMapper.select", id);
    }

    @Override
    public void insert(Member member) {
        session.insert("MemberMapper.insert", member);
    }

    @Override
    public void update(Member member) {
        session.update("MemberMapper.update", member);
    }

    @Override
    public void delete(String id) {
        session.delete("MemberMapper.delete", id);
    }

    @Override
    public int checkId(String id) {
        return session.selectOne("MemberMapper.chkId", id);
    }
}
