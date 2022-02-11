package com.board.basic.repository;

import com.board.basic.domain.Reply;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImp implements ReplyRepository{

    private final SqlSession session;

    @Override
    public List<Reply> selectAll(long contentId) {
        System.out.println("contentId = " + contentId);
        Reply reply = session.selectOne("ReplyMapper.selectOne", contentId);
        System.out.println("selectOne = " + reply);
        return session.selectList("ReplyMapper.selectAll", contentId);
    }

    @Override
    public void write(Reply reply) {
        session.insert("ReplyMapper.write", reply);
    }

    @Override
    public void modify(Reply reply) {
        session.update("ReplyMapper.modify", reply);
    }

    @Override
    public void delete(Reply reply) {
        session.delete("ReplyMapper.delete", reply);
    }
}
