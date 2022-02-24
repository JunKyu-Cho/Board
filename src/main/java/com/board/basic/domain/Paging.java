package com.board.basic.domain;

import lombok.Data;

@Data
public class Paging {
    private int page;        // 현재 페이지
    private int lastPage;       // 마지막 페이지
    private int startPage;      // 시작 페이지   ( n ~ n+5 )
    private int endPage;        // 끝 페이지    ( n ~ n+5 )
    private int cntPerPage;     // 페이지 당 게시물 수

    private final int cntPage = 5;    // 최대 페이지 수  < n n+1 n+2 n+3 n+4 >

    public Paging(int total, int nowPage, int cntPerPage) {
        this.page = nowPage;
        this.cntPerPage = cntPerPage;

        lastPage = (int)Math.ceil((double)total / (double)cntPerPage);
        endPage = (((int)Math.ceil((double)nowPage / (double)cntPage)) * cntPage) > getLastPage() ? getLastPage() : ((int)Math.ceil((double)nowPage / (double)cntPage)) * cntPage;
        startPage = (((int)Math.ceil((double)endPage / (double)cntPage) - 1) * cntPage) + 1;

        if(total == 0) {
            lastPage = 1;
            endPage = 1;
            startPage = 1;
        }
    }
}
