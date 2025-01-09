package com.project.file_compression.model;

public class RARFile {

    private int id;
    private String fileName;
    private String contentType;
    private long size;
    private byte[] content;
    private String compressionStatus;

    public RARFile() {
    }

    public RARFile(int id, String fileName, String contentType, long size, byte[] content, String compressionStatus) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
        this.content = content;
        this.compressionStatus = compressionStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getCompressionStatus() {
        return compressionStatus;
    }

    public void setCompressionStatus(String compressionStatus) {
        this.compressionStatus = compressionStatus;
    }

    @Override
    public String toString() {
        return "RARFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                ", compressionStatus='" + compressionStatus + '\'' +
                '}';
    }
}
