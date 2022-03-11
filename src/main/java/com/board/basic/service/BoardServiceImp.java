package com.board.basic.service;

import com.board.basic.repository.BoardRepository;
import com.board.basic.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImp implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public void viewCountUp(Long id, HttpServletRequest request, HttpServletResponse response) {

        try {

        } catch (Exception e) {
            throw e;
        }

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
            boardRepository.viewCountUp(id);

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
                boardRepository.viewCountUp(id);

                oldCookie.setValue(oldCookie.getValue() + "[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60);
                response.addCookie(oldCookie);
            }
        }

        return;
    }

    @Override
    public long write(Board board)
    {
        // 작성 시간
        SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        board.setRegDate(dateFormat.format(time));

        return boardRepository.write(board);
    }

    @Override
    public int readCount() {
        return boardRepository.boardCount();
    }

    @Override
    public List<Board> readList() {
        return boardRepository.readList();
    }

    @Override
    public Board read(Long id) {
        return boardRepository.readOne(id);
    }

    @Override
    public void update(Board board) {
        boardRepository.update(board);
    }

    @Override
    public void delete(Long id) {
        boardRepository.delete(id);
    }
}
