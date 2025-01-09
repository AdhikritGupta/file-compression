package com.project.file_compression.model;

import java.util.ArrayList;
import java.util.List;

public class RARFileList {

    private List<RARFile> rarFiles;

    public RARFileList() {
        this.rarFiles = new ArrayList<>();
    }

    public void addRARFile(RARFile rarFile) {
        this.rarFiles.add(rarFile);
    }

    public List<RARFile> getRarFiles() {
        return rarFiles;
    }

    public void setRarFiles(List<RARFile> rarFiles) {
        this.rarFiles = rarFiles;
    }

    @Override
    public String toString() {
        return "RARFileList{" +
                "rarFiles=" + rarFiles +
                '}';
    }
}
