package com.board.basic.service;

import com.board.basic.repository.BoardRepository;
import com.board.basic.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImp implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public void write(Board board) {
        boardRepository.write(board);
    }

    @Override
    public int readCount() {
        return boardRepository.boardCount();
    }

    @Override
    public List<Board> readList() {
        return boardRepository.readList();
    }

    @Override
    public Board read(Long id) {
        return boardRepository.readOne(id);
    }

    @Override
    public void update(Board board) {
        boardRepository.update(board);
    }

    @Override
    public void delete(Long id) {
        boardRepository.delete(id);
    }
}
