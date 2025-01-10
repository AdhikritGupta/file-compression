package com.project.file_compression.repository;


import com.project.file_compression.model.PDFFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PDFFileRepository extends MongoRepository<PDFFile, Integer> {
    // Additional query methods if needed
}

