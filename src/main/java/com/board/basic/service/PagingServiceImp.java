package com.board.basic.service;

import com.board.basic.domain.Board;
import com.board.basic.domain.Paging;
import com.board.basic.repository.PagingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagingServiceImp implements PagingService{

    private final PagingRepository pagingRepository;

    @Override
    public int countBoard() {
        return pagingRepository.countBoard();
    }

    @Override
    public List<Board> selectBoard(Paging page) {
        return pagingRepository.selectBoard(page);
    }
}
