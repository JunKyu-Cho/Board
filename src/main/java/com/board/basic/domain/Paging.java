package com.board.basic.domain;

import lombok.Data;

@Data
public class Paging {
    private int startPage;
    private int endPage;
    private int nowPage;
    private int contentCnt;
    private int cntPerPage;

    public void calStartPage(int nowPage) {

    }

    public void calEndPage(int total, int cntPerPage) {
        setEndPage((int)Math.ceil((double)total / (double)cntPerPage));
    }

}
