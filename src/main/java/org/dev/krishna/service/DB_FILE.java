package org.dev.krishna.service;

import lombok.Getter;

import java.io.File;

public enum DB_FILE {

    ACCOUNT("accountDBFile.txt"),
    TRANSACTION("transactionDBFile.txt"),
    SUMMARY("summaryDBFile.txt");

    DB_FILE(String fileName){
        this.name = fileName;
        String baseLocation="D:\\test\\dbFiles\\";
        this.path = baseLocation+fileName;
        this.file = new File(this.path);
    }
    @Getter
    private String name;
    @Getter
    private File file;
    @Getter
    private String path;
}