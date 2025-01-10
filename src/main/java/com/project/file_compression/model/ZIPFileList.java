package com.project.file_compression.model;

import java.util.ArrayList;
import java.util.List;

public class ZIPFileList {

    private List<ZIPFile> ZIPFiles;

    public ZIPFileList() {
        this.ZIPFiles = new ArrayList<>();
    }

    public void addRARFile(ZIPFile ZIPFile) {
        this.ZIPFiles.add(ZIPFile);
    }

    public List<ZIPFile> getRarFiles() {
        return ZIPFiles;
    }

    public void setRarFiles(List<ZIPFile> ZIPFiles) {
        this.ZIPFiles = ZIPFiles;
    }

    @Override
    public String toString() {
        return "RARFileList{" +
                "rarFiles=" + ZIPFiles +
                '}';
    }
}
