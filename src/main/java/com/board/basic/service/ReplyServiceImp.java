package com.board.basic.service;

import com.board.basic.domain.Reply;
import com.board.basic.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyServiceImp implements ReplyService{

    private final ReplyRepository replyRepository;

    @Override
    public List<Reply> selectAll(long contentId) {
        return replyRepository.selectAll(contentId);
    }

    @Override
    public void write(Reply reply) {
        replyRepository.write(reply);
    }

    @Override
    public void modify(Reply reply) {
        replyRepository.modify(reply);
    }

    @Override
    public void delete(Reply reply) {
        replyRepository.delete(reply);
    }
}
