package com.board.basic.controller;

import com.board.basic.domain.Member;
import com.board.basic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 로그인 화면 맵핑
    @GetMapping("/login")
    public String login(HttpServletRequest request) {

        String referer = request.getHeader("Referer");
        request.getSession().setAttribute("prevPage", referer);

        System.out.println("referer = " + referer);

        return"/boards/login";
    }

    // 로그인
    @PostMapping("/login/submit")
    public String loginSubmit(@ModelAttribute Member member, HttpServletRequest request) {
//    public String loginSubmit(@RequestBody Member member, HttpServletRequest request) {

        System.out.println("member = " + member);
        Member dbMember = new Member();
        dbMember = memberService.select(member.getId());
        System.out.println("dbMember = " + dbMember);

        if (member.getPassword().equals(dbMember.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", dbMember.getId());
        } else {
            return "/boards/login";
        }

        String redirectUrl = "";
        HttpSession session = request.getSession();
        if (session != null) {
            redirectUrl = (String) session.getAttribute("prevPage");
            if (redirectUrl != null) {
                session.removeAttribute("prevPage");
            } else {
                return "redirect:/board";
            }
        }

        // 이전 화면이 로그인 화면 일 경우
        if(redirectUrl.contains("/member/login"))
            return "redirect:/board";

        return "redirect:" + redirectUrl;
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
        System.out.println("아이디 중복 체크");
        System.out.println("member = " + member);
        return memberService.checkId(member.getId());
    }

    // 회원가입 - 등록
    @PostMapping("/register/submit")
//    public String registerMember(@RequestBody Member member) {
    public String registerMember(HttpServletResponse response, @ModelAttribute Member member) throws IOException {     //@ModelAttribute 사용

        System.out.println("회원가입");
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
