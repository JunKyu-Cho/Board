package com.board.basic.controller;

import com.board.basic.domain.Board;
import com.board.basic.domain.Member;
import com.board.basic.domain.Paging;
import com.board.basic.repository.BoardRepository;
import com.board.basic.service.BoardService;
import com.board.basic.service.MemberService;
import com.board.basic.service.PagingService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final PagingService pagingService;

/*    // 게시판 메인 맵핑 (게시물 리스트)
    @GetMapping("")
    public String mainView(Model model, Paging paging) {
        model.addAttribute("list", boardService.readList());
        *//*
        int totCount = pagingService.countBoard();

        Paging page = new Paging();
        model.addAttribute("paging", page);
        *//*
        
        //페이지 작업
        //https://freehoon.tistory.com/112
        
        return "/boards/board";
    }*/

    // 게시판 메인 맵핑 (게시물 리스트)
    @GetMapping("")
    public String mainView(Model model,
                           @RequestParam(required = false) String page,
                           @RequestParam(required = false) String cntPerPage) {

        if (page == null && cntPerPage == null) {
            page = "1";
            cntPerPage = "5";
        } else if (page == null) {
            page = "1";
        } else if (cntPerPage == null) {
            cntPerPage = "5";
        }

        int total = boardService.readCount();
        Paging paging = new Paging(total, Integer.parseInt(page), Integer.parseInt(cntPerPage));

        System.out.println("paging = " + paging);

        model.addAttribute("paging", paging);
        model.addAttribute("list", pagingService.selectBoard(paging));

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

    // 게시물 보기
    @GetMapping("contents")
    public String readContents(Model model, @RequestParam String id,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        // 조회 수 설정 //
        // 쿠키 조회
        Cookie oldCookie = null;
        Cookie cookies[] = request.getCookies();
        Map map = new HashMap();
        if(cookies != null) {
            for (Cookie cookie: cookies) {
                if(cookie.getName().equals("viewCount"))
                    oldCookie = cookie;
            }
        }

        // 쿠키 없을 경우 - 최초 등록
        if(oldCookie == null) {

            // 조회 수 증가
            boardService.viewCountUp(Long.parseLong(id));

            Cookie newCookie = new Cookie("viewCount", "[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60);
            response.addCookie(newCookie);
        }
        // 쿠키 있을 경우
        else {
            // 해당 게시물 id 확인
            if(!oldCookie.getValue().contains("[" + id.toString() + "]")) {

                // 조회 수 증가
                boardService.viewCountUp(Long.parseLong(id));

                oldCookie.setValue(oldCookie.getValue() + "[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60);
                response.addCookie(oldCookie);
            }
        }
        // 조회 수 설정 //

        // 게시물 조회
        Board board = boardService.read(Long.parseLong(id));

        // content => 라인별로 List 처리
        List<String> strList = Arrays.asList(board.getContent().split("\r\n"));

        model.addAttribute("board", board);
        model.addAttribute("content", strList);
        return "boards/content";
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
