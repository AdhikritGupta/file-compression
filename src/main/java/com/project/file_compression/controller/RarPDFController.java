package com.project.file_compression.controller;

import com.project.file_compression.model.RARFile;
import com.project.file_compression.model.RARFileList;
import com.project.file_compression.service.PDFCompressor;
import com.project.file_compression.service.PDFFileListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rarpdf")
public class RarPDFController {

    @Autowired
    private PDFCompressor pdfCompressor;

    @Autowired
    private PDFFileListService pdfFileListService;

    @PostMapping("/compress")
    public String compressToRar(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = new File(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());
            file.transferTo(tempFile);


            byte[] compressedContent = pdfCompressor.compressToZip(tempFile);


            int fid = pdfFileListService.getAllRARFiles().getRarFiles().size();
            RARFile rarFile = new RARFile(
                    fid++,
                    file.getOriginalFilename() + ".rar",
                    "application/vnd.rar",
                    compressedContent.length,
                    compressedContent,
                    "compressed"
            );

            pdfFileListService.addRARFile(rarFile);

            return "File successfully compressed to RAR and stored in the list.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error compressing file to RAR: " + e.getMessage();
        }
    }

    @GetMapping("/list")
    public Map<String, List<Map<String, Object>>> getRarFileList() {
        RARFileList list = pdfFileListService.getAllRARFiles();

        List<Map<String, Object>> rarDetails = new ArrayList<>();

        for (RARFile rarFile : list.getRarFiles()) {
            Map<String, Object> rarMap = new HashMap<>();
            rarMap.put("id", rarFile.getId());
            rarMap.put("fileName", rarFile.getFileName());
            rarMap.put("contentType", rarFile.getContentType());
            rarMap.put("size", rarFile.getSize());
            rarMap.put("compressionStatus", rarFile.getCompressionStatus());
            rarDetails.add(rarMap);
        }

        Map<String, List<Map<String, Object>>> response = new HashMap<>();
        response.put("rarFiles", rarDetails);
        return response;
    }

    @GetMapping("/file/{index}")
    public RARFile getRarFile(@PathVariable int index) {
        return pdfFileListService.getRARFile(index);
    }

    @GetMapping("/viewrar/{index}")
    public ResponseEntity<byte[]> viewRarFile(@PathVariable int index) {
        try {
            RARFile rarFile = pdfFileListService.getRARFile(index);

            if (rarFile == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + rarFile.getFileName() + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, rarFile.getContentType());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(rarFile.getContent());
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/view/{index}")
    public ResponseEntity<byte[]> viewUnzippedPDFFile(@PathVariable int index) {
        try {
            RARFile rarFile = pdfFileListService.getRARFile(index);

            if (rarFile == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }


            File tempZipFile = File.createTempFile("tempZIP", ".zip");
            try (FileOutputStream fos = new FileOutputStream(tempZipFile)) {
                fos.write(rarFile.getContent());
            }


            File outputFolder = new File(System.getProperty("java.io.tmpdir"), "extractedPDF");
            if (!outputFolder.exists()) {
                outputFolder.mkdir();
            }


            try (java.util.zip.ZipInputStream zis = new java.util.zip.ZipInputStream(new FileInputStream(tempZipFile))) {
                java.util.zip.ZipEntry zipEntry;
                while ((zipEntry = zis.getNextEntry()) != null) {
                    if (zipEntry.getName().endsWith(".pdf")) {
                        File extractedPDF = new File(outputFolder, zipEntry.getName());


                        try (FileOutputStream fos = new FileOutputStream(extractedPDF)) {
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = zis.read(buffer)) > 0) {
                                fos.write(buffer, 0, length);
                            }
                        }


                        byte[] pdfContent;
                        try (FileInputStream fis = new FileInputStream(extractedPDF)) {
                            pdfContent = fis.readAllBytes();
                        }


                        HttpHeaders headers = new HttpHeaders();
                        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + extractedPDF.getName() + "\"");
                        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

                        return ResponseEntity.ok()
                                .headers(headers)
                                .body(pdfContent);
                    }
                }
            }


            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
