package com.board.basic.service;

import com.board.basic.domain.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> selectAll(long contentId);
    void write(Reply reply);
    void modify(Reply reply);
    void delete(Reply reply);
}
