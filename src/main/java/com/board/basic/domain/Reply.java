package com.board.basic.domain;

import lombok.Data;

@Data
public class Reply {
    private long replyId;
    private long contentId;
    private String writer;
    private String content;
    private String regDate;

    public Reply(long contentId, String writer, String content, String regDate) {
        this.contentId = contentId;
        this.writer = writer;
        this.content = content;
        this.regDate = regDate;
    }
}
