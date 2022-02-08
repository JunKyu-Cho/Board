package com.board.basic.repository;

import com.board.basic.domain.Board;
import com.board.basic.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImp implements BoardRepository{

    /*
    private final BoardMapper mapper;

    @Override
    public int boardCount() {
//        return mapper.boardCount();
        return 0;
    }

    @Override
    public List<Board> readList() {
        return mapper.getList();
    }

    @Override
    public Board readOne(Long id) {
        return mapper.select(id);
    }

    @Override
    public void write(Board board) {
        SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        board.setRegDate(dateFormat.format(time));

        mapper.insert(board);
    }

    @Override
    public void update(Board board) {
        mapper.update(board);
    }

    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }
    */


    private final SqlSession session;

    @Override
    public void viewCountUp(Long id) {
        session.update("BoardMapper.viewCountUp", id);
    }

    @Override
    public int boardCount() {
        return readList().size();
    }

    @Override
    public List<Board> readList() {
        return  session.selectList("BoardMapper.getList");
    }

    @Override
    public Board readOne(Long id) {
        return session.selectOne("BoardMapper.select", id);
    }

    @Override
    public void write(Board board) {
        session.insert("BoardMapper.insert", board);
    }

    @Override
    public void update(Board board) {
        session.update("BoardMapper.update", board);
    }

    @Override
    public void delete(Long id) {
        session.delete("BoardMapper.delete", id);
    }

}
