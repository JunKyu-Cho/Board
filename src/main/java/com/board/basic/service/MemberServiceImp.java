package com.board.basic.service;

import com.board.basic.domain.Member;
import com.board.basic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Member select(String id) {
        return memberRepository.select(id);
    }

    @Override
    public void insert(Member member) {
        memberRepository.insert(member);
    }

    @Override
    public void update(Member member) {
        memberRepository.update(member);
    }

    @Override
    public void delete(String id) {
        memberRepository.delete(id);
    }

    @Override
    public int checkId(String id) {
        return memberRepository.checkId(id);
    }
}
