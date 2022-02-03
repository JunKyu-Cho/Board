package com.board.basic.controller;

import com.board.basic.domain.Board;
import com.board.basic.domain.Member;
import com.board.basic.repository.BoardRepository;
import com.board.basic.service.BoardService;
import com.board.basic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

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
    public String loginSubmit(@ModelAttribute Member member, HttpServletRequest request) {

        Member dbMember = new Member();
        dbMember = memberService.select(member.getId());

        if (member.getPassword().equals(dbMember.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", dbMember.getId());
            return "redirect:/board";
        } else {
            return "redirect:/board/login";
        }
    }

    // 회원가입 화면
    @GetMapping("/register")
    public String register() {
        return "/boards/register";
    }

    // 회원가입 - 아이디 중복 체크
    @PostMapping("/register/chkid")
    @ResponseBody
    public int checkId(@RequestBody Member member) throws Exception {
        return memberService.checkId(member.getId());
    }

    // 회원가입 - 등록
    @PostMapping("/register/submit")
//    public String registerMember(@RequestBody Member member) {
    public String registerMember(HttpServletResponse response, @ModelAttribute Member member) throws Exception {

        System.out.println("member = " + member);

        try {
            memberService.insert(member);

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();

            out.println("<script>alert('회원가입 완료');</script>");
            out.flush();

        } catch (Exception e) {
            throw e;
        }

        return "/boards/login";
    }

    @GetMapping("/write")
    public String write() {
        return"/boards/write";
    }
}
