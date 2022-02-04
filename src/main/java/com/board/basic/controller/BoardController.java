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

    // 게시판 메인 맵핑 (게시물 리스트)
    @GetMapping("")
    public String mainView(Model model) {
        model.addAttribute("list", boardService.readList());
        return "/boards/board";
    }

    // 글 작성 화면 맵핑
    @GetMapping("/write")
    public String write() {
        return"/boards/write";
    }
    
    // 글 작성
    @PostMapping("/write/add")
    public String addContext(Board board) {         // @ModelAttribute는 생략 가능
        System.out.println("board = " + board);

        boardService.write(board);
        return "redirect:/board";
    }

    // 로그인 화면 맵핑
    @GetMapping("/login")
    public String login() {
        return"/boards/login";
    }

    // 로그인
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

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();

        String url = request.getHeader("REFERER");
        System.out.println("url = " + url);
        return "redirect:" + url;
    }

    // 회원가입 화면 맵핑
    @GetMapping("/register")
    public String register() {
        return "/boards/register";
    }

    // 회원가입 - 아이디 중복 체크
    @PostMapping("/register/chkid")
    @ResponseBody
    public int checkId(@RequestBody Member member) throws Exception {       // @RequestBody 사용 => ajax 사용 하여 json 형식 data 전달 필요
        return memberService.checkId(member.getId());
    }

    // 회원가입 - 등록
    @PostMapping("/register/submit")
//    public String registerMember(@RequestBody Member member) {
    public String registerMember(HttpServletResponse response, @ModelAttribute Member member) throws IOException {     //@ModelAttribute 사용

        System.out.println("member = " + member);

        try {
            memberService.insert(member);

            /* Controller 에서 Alert 창 출력 방법 */
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();

            out.println("<script>alert('회원가입 완료');</script>");
            out.flush();
            /* Controller 에서 Alert 창 출력 방법 */

        } catch (IOException e) {
            throw e;
        }

        return "/boards/login";
    }
}
