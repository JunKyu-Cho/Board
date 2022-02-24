package com.board.basic.service;

import com.board.basic.repository.FileRepository;
import com.board.basic.domain.UpLoadFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService{
    private final FileRepository fileRepository;

    @Override
    public UpLoadFile getFile(String fileName) {
        return fileRepository.getFile(fileName);
    }

    @Override
    public void uploadFile(long id, List<MultipartFile> files) throws IOException {
        for(MultipartFile file : files){
            // UUID - 고유번호를 얻기 위함, getExtension() - 파일 확장자 확인
            String newFilename = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

            // 저장 파일 Full Path
            String fullPath = "C:/temp/"+ newFilename;

            // 원본 파일 저장
            file.transferTo(new File(fullPath));

            // 파일 저장 정보 (게시글 id, Full Path, 저장 파일명, 원본 파일명)
            UpLoadFile upFile = new UpLoadFile(id, fullPath, newFilename, file.getOriginalFilename());
            System.out.println("upFile = " + upFile);

            fileRepository.uploadFile(upFile);
        }
    }

    public List<UpLoadFile> uploadFileList(long id) {
        return fileRepository.uploadFileList(id);
    }
}
