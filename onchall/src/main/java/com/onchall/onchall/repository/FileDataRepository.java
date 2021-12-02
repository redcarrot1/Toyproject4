package com.onchall.onchall.repository;

import com.onchall.onchall.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData, Long> {
    Optional<FileData> findByUploadName(String uploadName);
}
