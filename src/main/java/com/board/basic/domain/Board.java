package com.board.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Board {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String regDate;
}
