package com.board.basic.repository;

import com.board.basic.domain.Board;
import com.board.basic.domain.Paging;

import java.util.List;

public interface PagingRepository {
    int countBoard();
    List<Board> selectBoard(Paging page);
}
