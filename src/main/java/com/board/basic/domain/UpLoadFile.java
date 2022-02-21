package com.board.basic.domain;

import lombok.Data;

@Data
public class UpLoadFile {
    long id;
    String fullPath;
    String fileName;
    String oriFilename;

    public UpLoadFile(long id, String fullPath, String fileName, String oriFilename) {
        this.id = id;
        this.fullPath = fullPath;
        this.fileName = fileName;
        this.oriFilename = oriFilename;
    }
}
