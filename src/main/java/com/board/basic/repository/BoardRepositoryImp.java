package com.board.basic.repository;

import com.board.basic.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepositoryImp implements BoardRepository{

    @Override
    public int boardCount() {
//        return mapper.boardCount();
        return 0;
    }

    @Override
    public List<Board> readList() {
//        return sqlSession.getMapper(BoardMapper.class).getList();
        return null;
    }

    @Override
    public Board readOne(Long id) {
        return null;
    }

    @Override
    public void write(Board board) {
//        sqlSession.inse rt("Mapper.insert", board);
    }

    @Override
    public void update(Long id, Board board) {

    }

    @Override
    public void delete(Long id) {

    }
}
