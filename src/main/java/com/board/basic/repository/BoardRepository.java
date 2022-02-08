package com.board.basic.repository;

import com.board.basic.domain.Board;

import java.util.List;

public interface BoardRepository {
    void viewCountUp(Long id);
    int boardCount();
    List<Board> readList();
    Board readOne(Long id);
    void write(Board board);
    void update(Board board);
    void delete(Long id);
}
