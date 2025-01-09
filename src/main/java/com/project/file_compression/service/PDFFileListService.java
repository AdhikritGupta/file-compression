package com.project.file_compression.service;

import com.project.file_compression.model.PDFFile;
import com.project.file_compression.model.PDFFileList;
import com.project.file_compression.model.RARFile;
import com.project.file_compression.model.RARFileList;
import org.springframework.stereotype.Service;

@Service
public class PDFFileListService {

    PDFFileList pdfFileList = new PDFFileList(); // In-memory list of PDFFile objects
    RARFileList rarFileList = new RARFileList(); // In-memory list of RARFile objects


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


    public void addRARFile(RARFile rarFile) {
        rarFileList.addRARFile(rarFile);
    }


    public RARFileList getAllRARFiles() {
        return rarFileList;
    }

    public RARFile getRARFile(int index) {
        if (index >= 0 && index < rarFileList.getRarFiles().size()) {
            return rarFileList.getRarFiles().get(index);
        }
        return null;
    }
}
