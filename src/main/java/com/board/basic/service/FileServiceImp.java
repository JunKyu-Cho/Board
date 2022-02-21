package com.board.basic.service;

import com.board.basic.repository.FileRepository;
import com.board.basic.domain.UpLoadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService{
    private final FileRepository fileRepository;

    @Override
    public UpLoadFile getFile(String fileName) {
        return fileRepository.getFile(fileName);
    }
    @Override
    public void uploadFile(UpLoadFile file) {
        fileRepository.uploadFile(file);
    }

    public List<UpLoadFile> uploadFileList(long id) {
        return fileRepository.uploadFileList(id);
    }
}
