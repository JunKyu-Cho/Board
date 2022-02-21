package com.board.basic.repository;

import com.board.basic.domain.Board;
import com.board.basic.domain.UpLoadFile;

import java.util.List;

public interface BoardRepository {
    void viewCountUp(Long id);
    int boardCount();
    List<Board> readList();
    Board readOne(Long id);
    long write(Board board);
    void update(Board board);
    void delete(Long id);

}
