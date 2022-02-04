package com.board.basic.repository;

import com.board.basic.domain.Board;
import com.board.basic.domain.Paging;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PagingRepositoryImp implements PagingRepository{

    private final SqlSession session;

    @Override
    public int countBoard() {
        return session.selectOne("BoardMapper.boardList");
    }

    @Override
    public List<Board> selectBoard(Paging page) {
        return session.selectList("BoardMapper.selectBoard", page);
    }
}
