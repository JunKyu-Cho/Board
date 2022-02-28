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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

    // summernote에 이미지 로드
    @PostMapping("/write/imageUpload")
    @ResponseBody
    public String imageUpload(@RequestParam("file") MultipartFile file) throws IOException {

        String filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        // 저장 파일 Full Path
        String fullPath = "C:/temp/"+ filename;

        // 1. 원본 파일 저장
        file.transferTo(new File(fullPath));

        // /summernoteImage/ => 정적 리소스 설정 : WebConfig에서 경로 설정
        return "/summernoteImage/" + filename;
    }

    // 글 작성
    @PostMapping("/write/add")
    public String addContext(Board board, HttpServletRequest request,
                             @RequestBody List<MultipartFile> files) throws IOException {         // @ModelAttribute는 생략 가능

        String userId =(String)request.getSession().getAttribute("userId");

        if(userId != null) {
            board.setWriter(userId);

            board.setViewCount(0);
            boardService.write(board);
            System.out.println("board = " + board);
            System.out.println("files = " + files);

            fileService.uploadFile(board.getId(), files);
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
        boardService.viewCountUp(Long.parseLong(id), request, response);

        // 게시물 조회
        Board board = boardService.read(Long.parseLong(id));

        // 댓글 조회
        List<Reply> replyList = replyService.selectAll(Long.parseLong(id));

        // 댓글 정보, 댓글 라인별 텍스트 HaspMap (Haspmap의 순서를 보장 받기 위함 => LinkedHashMap)
//        Map<Reply, List<String>> replyInfo = new LinkedHashMap<>();
//        for(Reply r : replyList) {
//            System.out.println("r = " + r);
//            List<String> contentList = Arrays.asList(r.getContent().split("\r\n"));
//            replyInfo.put(r, contentList);
//        }

        // Upload File 조회
       List<UpLoadFile> fileList = fileService.uploadFileList(Long.parseLong(id));

        model.addAttribute("board", board);         // 게시물 정보
//        model.addAttribute("replyList", replyInfo); // 댓글 정보
        model.addAttribute("replyList", replyList);
        model.addAttribute("pageNo", page);         // 페이지 번호 (목록 이동 시 필요)
        model.addAttribute("fileList", fileList);   // 다운로드 파일 리스트
        return "/boards/content";
    }

    // 게시물 삭제
    @GetMapping("/delete")
    public String deleteContent(@RequestParam String id) {

        System.out.println("id = " + id);
        boardService.delete(Long.parseLong(id));
        return "redirect:/board";
    }

    // 게시물 수정 화면 맵핑
    @PostMapping("/modify")
    public String modifyContent(Model model, Board board, @RequestParam String page) {
        System.out.println("board = " + board);
        System.out.println("page = " + page);

        model.addAttribute("board", board);
        model.addAttribute("pageNo", page);
        return"/boards/modify";
    }

    // 게시물 수정
    @PostMapping("/modify/submit")
    public String modifySubmit(Board board, @RequestParam String page,
                               @RequestBody List<MultipartFile> files) throws IOException {
        System.out.println("board = " + board);
        System.out.println("page = " + page);

        boardService.update(board);
        fileService.uploadFile(board.getId(), files);
        return "redirect:/board/contents?id=" + board.getId() + "&page=" + page;
    }

    // 파일 다운로드
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileName")String fileName) throws IOException {

        System.out.println("fileName = " + fileName);

        UpLoadFile file = fileService.getFile(fileName);    // 파일 이름으로 DB 조회
        Path path = Paths.get(file.getFullPath());          // 파일 Full path

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriFilename() + "\"")
                .body(resource);
    }
}
