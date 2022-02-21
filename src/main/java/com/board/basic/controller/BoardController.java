package com.board.basic.controller;

import com.board.basic.domain.*;
import com.board.basic.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final PagingService pagingService;
    private final ReplyService replyService;
    private final FileService fileService;

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
    public String addContext(Board board, HttpServletRequest request, @RequestBody List<MultipartFile> files) throws IOException {         // @ModelAttribute는 생략 가능

        String userId =(String)request.getSession().getAttribute("userId");
        if(userId != null) {
            board.setWriter(userId);

            board.setViewCount(0);
            boardService.write(board);
            System.out.println("board = " + board);
            System.out.println("files = " + files);

            for(MultipartFile file : files){
                // UUID - 고유번호를 얻기 위함, getExtension() - 파일 확장자 확인
                String newFilename = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

                // 저장 파일 Full Path
                String fullPath = "C:/temp/"+ newFilename;

                // 1. 원본 파일 저장
                file.transferTo(new File(fullPath));

                // 2. 파일 정보 (게시글 id, Full Path, 저장 파일명, 원본 파일명)
                UpLoadFile upFile = new UpLoadFile(board.getId(), fullPath, newFilename, file.getOriginalFilename());
                System.out.println("upFile = " + upFile);

                fileService.uploadFile(upFile);
            }
        }

        return "redirect:/board";
    }

    // 게시물 보기
    @GetMapping("/contents")
    public String readContents(Model model, @RequestParam String id, @RequestParam String page,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        System.out.println("page = " + page);

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
            System.out.println("쿠키 없음");
            System.out.println("id = " + id);
            // 조회 수 증가
            boardService.viewCountUp(Long.parseLong(id));

            Cookie newCookie = new Cookie("viewCount", "[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60);
            response.addCookie(newCookie);
        }
        // 쿠키 있을 경우
        else {
            System.out.println("쿠키 있음");
            // 해당 게시물 id 확인
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {

                System.out.println("해당 게시물 쿠키가 없을 경우");
                // 조회 수 증가
                boardService.viewCountUp(Long.parseLong(id));

                oldCookie.setValue(oldCookie.getValue() + "[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60);
                response.addCookie(oldCookie);
            }
        }

        // 게시물 조회
        Board board = boardService.read(Long.parseLong(id));
        // content => 라인별로 List 처리
        List<String> strList = Arrays.asList(board.getContent().split("\r\n"));

        // 댓글 조회
        List<Reply> replyList = replyService.selectAll(Long.parseLong(id));

        // 댓글 정보, 댓글 라인별 텍스트 HaspMap (Haspmap의 순서를 보장 받기 위함 => LinkedHashMap)
        Map<Reply, List<String>> replyInfo = new LinkedHashMap<>();
        for(Reply r : replyList) {
            System.out.println("r = " + r);
            List<String> contentList = Arrays.asList(r.getContent().split("\r\n"));
            replyInfo.put(r, contentList);
        }

        // Upload File 조회
       List<UpLoadFile> fileList = fileService.uploadFileList(Long.parseLong(id));
        System.out.println("fileList = " + fileList);

        model.addAttribute("board", board);         // 게시물 정보
        model.addAttribute("content", strList);     // 게시물 내용
        model.addAttribute("replyList", replyInfo); // 댓글 정보
        model.addAttribute("pageNo", page);         // 페이지 번호 (목록 이동 시 필요)
        model.addAttribute("fileList", fileList);   // 다운로드 파일 리스트
        return "/boards/content";
    }

    @GetMapping("/delete")
    public String deleteContent(@RequestParam String id) {

        System.out.println("id = " + id);
        boardService.delete(Long.parseLong(id));
        return "redirect:/board";
    }

    @PostMapping("/modify")
    public String modifyContent(Model model, Board board, @RequestParam String page) {
        System.out.println("board = " + board);
        System.out.println("page = " + page);

        model.addAttribute("board", board);
        model.addAttribute("pageNo", page);
        return"/boards/modify";
    }

    @PostMapping("/modify/submit")
    public String modifySubmit(Board board, @RequestParam String page) {
        System.out.println("board = " + board);
        System.out.println("page = " + page);

        boardService.update(board);
        return "redirect:/board/contents?id=" + board.getId() + "&page=" + page;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileName")String fileName) throws IOException {

        System.out.println("fileName = " + fileName);

        UpLoadFile file = fileService.getFile(fileName);
        Path path = Paths.get(file.getFullPath());

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriFilename() + "\"")
                .body(resource);
    }
}
