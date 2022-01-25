package com.board.basic.mapper;

import com.board.basic.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<Board> getList();
}
