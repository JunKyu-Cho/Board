package com.board.basic.service;

import com.board.basic.domain.Reply;
import com.board.basic.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        // 작성 시간
        SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        reply.setRegDate(dateFormat.format(time));

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
