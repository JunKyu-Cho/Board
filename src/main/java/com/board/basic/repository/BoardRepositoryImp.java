package com.board.basic.repository;

import com.board.basic.domain.Board;
import com.board.basic.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImp implements BoardRepository{

    private final BoardMapper mapper;

    @Override
    public int boardCount() {
//        return mapper.boardCount();
        return 0;
    }

    @Override
    public List<Board> readList() {
        return mapper.getList();
//        return null;
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
