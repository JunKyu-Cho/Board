package com.board.basic.repository;

import com.board.basic.domain.UpLoadFile;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImp implements FileRepository{

    private final SqlSession session;

    @Override
    public UpLoadFile getFile(String fileName) {
        return session.selectOne("FileMapper.selectOne", fileName);
    }

    @Override
    public void uploadFile(UpLoadFile file) {
        session.insert("FileMapper.insert", file);
    }

    public List<UpLoadFile> uploadFileList(long id) {
        return session.selectList("FileMapper.selectAll", id);
    }
}
