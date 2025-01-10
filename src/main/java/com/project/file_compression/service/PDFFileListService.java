package com.project.file_compression.service;

import com.project.file_compression.model.PDFFile;
import com.project.file_compression.model.PDFFileList;
import com.project.file_compression.model.ZIPFile;
import com.project.file_compression.model.ZIPFileList;
import org.springframework.stereotype.Service;

@Service
public class PDFFileListService {

    PDFFileList pdfFileList = new PDFFileList(); // In-memory list of PDFFile objects
    ZIPFileList ZIPFileList = new ZIPFileList(); // In-memory list of RARFile objects


    public void addPDFFile(PDFFile pdfFile) {
        pdfFileList.addPDFFile(pdfFile);
    }


    public PDFFileList getAllPDFFiles() {
        return pdfFileList;
    }

    public PDFFile getPDFFile(int index) {

        if (index >= 0 && index < pdfFileList.getPdfFiles().size()) {
            return pdfFileList.getPdfFiles().get(index);
        }
        return null;
    }


    public void addRARFile(ZIPFile ZIPFile) {
        ZIPFileList.addRARFile(ZIPFile);
    }


    public ZIPFileList getAllRARFiles() {
        return ZIPFileList;
    }

    public ZIPFile getRARFile(int index) {
        if (index >= 0 && index < ZIPFileList.getRarFiles().size()) {
            return ZIPFileList.getRarFiles().get(index);
        }
        return null;
    }
}
