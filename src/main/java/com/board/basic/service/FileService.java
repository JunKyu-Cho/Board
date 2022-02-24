package com.board.basic.service;

import com.board.basic.domain.UpLoadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    UpLoadFile getFile(String fileName);
    void uploadFile(long id, List<MultipartFile> files) throws IOException;
    List<UpLoadFile> uploadFileList(long id);
}
