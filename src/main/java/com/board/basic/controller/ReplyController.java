package com.board.basic.controller;

import com.board.basic.domain.Reply;
import com.board.basic.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/write")
    public String writeReply(Reply reply) {

        System.out.println("reply = " + reply);
        String contentId = Long.toString(reply.getContentId());

        replyService.write(reply);
        return "redirect:/board/contents?id=" + contentId;
    }

    @PostMapping("/delete")
    public String deleteReply(Reply reply) {
        System.out.println("delete Reply");
        System.out.println("reply = " + reply);
        String contentId = Long.toString(reply.getContentId());

        replyService.delete(reply);
        return "redirect:/board/contents?id=" + contentId;
    }
}
