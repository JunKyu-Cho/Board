package com.board.basic.repository;

import com.board.basic.domain.Board;

import java.util.List;

public interface BoardRepository {

    int boardCount();
    List<Board> readList();
    Board readOne(Long id);
    void write(Board board);
    void update(Long id, Board board);
    void delete(Long id);
}
