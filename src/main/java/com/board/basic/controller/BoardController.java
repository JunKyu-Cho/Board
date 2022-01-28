package com.board.basic.controller;

import com.board.basic.domain.Board;
import com.board.basic.domain.Member;
import com.board.basic.repository.BoardRepository;
import com.board.basic.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("")
    public String test(Model model) {
        model.addAttribute("list", boardService.readList());
        return "/boards/list";
    }

    @GetMapping("/login")
    public String login() {
        return"/boards/login";
    }

    @PostMapping("/login/submit")
    public String loginSubmit(@RequestParam("id") String id, @RequestParam("password") String password) {
        System.out.println("id = " + id);
        System.out.println("password = " + password);
        System.out.println("Login Submit");
//        return"/boards/login";
        return "redirect:/board/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/boards/register";
    }

    @PostMapping("/register/submit")
    public String registerMember(@RequestBody Member member) {

        System.out.println("member = " + member);
        return "/boards/login";
    }

    @GetMapping("/write")
    public String write() {
        return"/boards/write";
    }
}
