package com.board.basic.repository;

import com.board.basic.domain.UpLoadFile;

import java.util.List;

public interface FileRepository {
    UpLoadFile getFile(String fileName);
    void uploadFile(UpLoadFile file);
    List<UpLoadFile> uploadFileList(long id);
}
