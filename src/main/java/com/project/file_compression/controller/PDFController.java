package com.project.file_compression.controller;

import com.project.file_compression.model.PDFFile;
import com.project.file_compression.model.PDFFileList;
import com.project.file_compression.service.PDFCompressor;
import com.project.file_compression.service.PDFFileListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PDFController {

    @Autowired
    private PDFCompressor pdfCompressor;

    @Autowired
    private PDFFileListService pdfFileListService;


    @PostMapping("/compress")
    public String compressPDF(@RequestParam("file") MultipartFile file) {
        try {

            File tempFile = new File(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());
            file.transferTo(tempFile);


            byte[] compressedContent = pdfCompressor.compressPDF(tempFile);


            int fid = pdfFileListService.getAllPDFFiles().getPdfFiles().size();
            PDFFile compressedPDF = new PDFFile(
                    fid++,
                    file.getOriginalFilename(),
                    "application/pdf",
                    compressedContent.length,
                    compressedContent,
                    "compressed"
            );


            pdfFileListService.addPDFFile(compressedPDF);


            return "PDF successfully compressed and stored in the list.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error compressing PDF: " + e.getMessage();
        }
    }


    @GetMapping("/list")
    public PDFFileList getPdfList() {
        return pdfFileListService.getAllPDFFiles(); // Retrieve the list of stored PDF files
    }


    @GetMapping("/file/{index}")
    public PDFFile getPDFFile(@PathVariable int index) {
        // Retrieve the PDF file based on the index
        return pdfFileListService.getPDFFile(index);
    }

    @GetMapping("/view/{index}")
    public ResponseEntity<byte[]> viewPDFFile(@PathVariable int index) {
        try {
            PDFFile pdfFile = pdfFileListService.getPDFFile(index); // Get the PDF file by index

            if (pdfFile == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }


            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + pdfFile.getFileName() + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, pdfFile.getContentType());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfFile.getContent()); // Return the file content
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
