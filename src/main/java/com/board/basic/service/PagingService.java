package com.board.basic.service;

import com.board.basic.domain.Board;
import com.board.basic.domain.Paging;

import java.util.List;

public interface PagingService {
    int countBoard();
    List<Board> selectBoard(Paging page);
}
