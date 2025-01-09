package com.project.file_compression.service;

import com.project.file_compression.model.PDFFile;
import com.project.file_compression.model.PDFFileList;
import org.springframework.stereotype.Service;

@Service
public class PDFFileListService {

    PDFFileList pdfFileList = new PDFFileList(); // In-memory list of PDFFile objects

    // Method to add a PDF to the list
    public void addPDFFile(PDFFile pdfFile) {
        pdfFileList.addPDFFile(pdfFile);
    }

    // Method to retrieve all PDF files in the list
    public PDFFileList getAllPDFFiles() {
        return pdfFileList;
    }

    public PDFFile getPDFFile(int index) {
        // Check if the index is valid and within bounds of the list
        if (index >= 0 && index < pdfFileList.getPdfFiles().size()) {
            return pdfFileList.getPdfFiles().get(index);
        }
        return null;  // Return null if the index is out of bounds
    }

    // You can implement more methods here like saving to a database or clearing the list
    // For example, saving to a database or clearing the list
    // public void saveToDatabase() {
    //     // Implement database saving logic
    // }

    // public void clearPDFFiles() {
    //     pdfFileList.clearList();
    // }
}
