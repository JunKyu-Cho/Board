package com.board.basic.service;

import com.board.basic.domain.Board;

import java.util.List;

public interface BoardService {
    void viewCountUp(Long id);
    void write(Board board);
    int readCount();
    List<Board> readList();
    Board read(Long id);
    void update(Board board);
    void delete(Long id);
}
