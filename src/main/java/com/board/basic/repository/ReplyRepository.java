package com.board.basic.repository;

import com.board.basic.domain.Reply;

import java.util.List;

public interface ReplyRepository {
    List<Reply> selectAll(long contentId);
    void write(Reply reply);
    void modify(Reply reply);
    void delete(Reply reply);
}
