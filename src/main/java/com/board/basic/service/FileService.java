package com.board.basic.service;

import com.board.basic.domain.UpLoadFile;

import java.util.List;

public interface FileService {

    UpLoadFile getFile(String fileName);
    void uploadFile(UpLoadFile file);
    List<UpLoadFile> uploadFileList(long id);
}
