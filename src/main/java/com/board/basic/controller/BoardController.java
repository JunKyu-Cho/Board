package com.board.basic.controller;

import com.board.basic.domain.Board;
import com.board.basic.repository.BoardRepository;
import com.board.basic.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
//    private final BoardRepository boardRepository;

    @GetMapping("/hello")
    public String hello() {
        return "boards/hello";
    }

    @GetMapping("/test")
    public String test(Model model) {
//        model.addAttribute("cnt", boardService.readCount());
//        model.addAttribute("members", boardService.readList());
//        model.addAttribute("cnt", boardRepository.boardCount());
//        model.addAttribute("test", boardRepository.readList());

        return "/boards/hello";
    }
}
