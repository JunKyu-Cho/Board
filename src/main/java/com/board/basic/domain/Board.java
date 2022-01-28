package com.board.basic.domain;

import lombok.*;

@Data
public class Board {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String regDate;

    public Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
