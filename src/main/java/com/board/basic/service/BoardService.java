package com.board.basic.service;

import com.board.basic.domain.Board;
import com.board.basic.domain.UpLoadFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BoardService {
    void viewCountUp(Long id, HttpServletRequest request, HttpServletResponse response);
    long write(Board board);
    int readCount();
    List<Board> readList();
    Board read(Long id);
    void update(Board board);
    void delete(Long id);
}
