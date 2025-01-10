package com.project.file_compression.repository;

import com.project.file_compression.model.ZIPFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZIPFileRepository extends MongoRepository<ZIPFile, Integer> {
    // Additional query methods if needed

}
