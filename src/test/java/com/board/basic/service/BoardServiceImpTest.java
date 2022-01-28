package com.board.basic.service;

import com.board.basic.BasicApplication;
import com.board.basic.domain.Board;
import com.board.basic.repository.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class BoardServiceImpTest {

    @Autowired BoardService boardService;

    @Test
    void select() {
        List<Board> boards = boardService.readList();
        assertThat(boards.size()).isEqualTo(1);
    }

    @Test
    void selectOne() {
        Board board = boardService.read(1L);
        assertThat(board.getWriter()).isEqualTo("작성자");

        System.out.println("board = " + board);
    }

    @Test
    void insert() {
        Board board = new Board("Test Title", "Test Content", "Test Writer");
        boardService.write(board);

        Board read = boardService.read(1L);

        assertThat(board).isEqualTo(read);
    }

    @Test
    void update() {
        Board board = boardService.read(2L);
        board.setContent("Update Content");

        boardService.update(board);

        Board read = boardService.read(2L);
        assertThat(read.getContent()).isEqualTo("Update Content");

    }

    @Test
    void delete() {

        boardService.delete(2L);

        List<Board> boards = boardService.readList();
        assertThat(boards.size()).isEqualTo(1);
    }

}