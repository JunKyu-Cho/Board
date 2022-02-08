package com.board.basic.service;

import com.board.basic.repository.BoardRepository;
import com.board.basic.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImp implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public void viewCountUp(Long id) {
        boardRepository.viewCountUp(id);
    }

    @Override
    public void write(Board board)
    {
        // 작성 시간
        SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        board.setRegDate(dateFormat.format(time));

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
