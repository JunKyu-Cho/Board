package com.board.basic.controller;

import com.board.basic.domain.Board;
import com.board.basic.domain.Member;
import com.board.basic.domain.Paging;
import com.board.basic.domain.Reply;
import com.board.basic.repository.BoardRepository;
import com.board.basic.service.BoardService;
import com.board.basic.service.MemberService;
import com.board.basic.service.PagingService;
import com.board.basic.service.ReplyService;
import com.sun.net.httpserver.HttpsServer;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.util.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final PagingService pagingService;
    private final ReplyService replyService;

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
    public String addContext(Board board, HttpServletRequest request) {         // @ModelAttribute는 생략 가능

        String userId =(String)request.getSession().getAttribute("userId");
        if(userId != null) {
            board.setWriter(userId);

            boardService.write(board);
            System.out.println("board = " + board);
        }

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

        System.out.println("board = " + board);

        List<Reply> replyList = replyService.selectAll(Long.parseLong(id));

        // content => 라인별로 List 처리
        List<String> strList = Arrays.asList(board.getContent().split("\r\n"));

        // 댓글 정보, 댓글 라인별 텍스트 HaspMap (Haspmap의 순서를 보장 받기 위함 => LinkedHashMap)
        Map<Reply, List<String>> replyInfo = new LinkedHashMap<>();
        for(Reply r : replyList) {
            System.out.println("r = " + r);
            List<String> contentList = Arrays.asList(r.getContent().split("\r\n"));
            replyInfo.put(r, contentList);
        }


        model.addAttribute("board", board);
        model.addAttribute("content", strList);
        model.addAttribute("replyList", replyInfo);
        return "/boards/content";
    }

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

        if (member.getPassword().equals(dbMember.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", dbMember.getId());
        } else {
            System.out.println("/boards/login");
            return "/boards/login";
        }

        String redirectUrl = "";
        HttpSession session = request.getSession();
        if (session != null) {
            redirectUrl = (String) session.getAttribute("prevPage");
            if (redirectUrl != null) {
                session.removeAttribute("prevPage");
            } else {
                return "redirect:/board/login";
            }
        }

        System.out.println(redirectUrl);
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
    public int checkId(@ModelAttribute Member member) throws Exception {       // @RequestBody 사용 => ajax 사용 하여 json 형식 data 전달 필요
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

    @PostMapping("/reply/write")
    public String writeReply(Reply reply) {

        System.out.println("reply = " + reply);
        String contentId = Long.toString(reply.getContentId());

        replyService.write(reply);
        return "redirect:/board/contents?id=" + contentId;
    }
}
