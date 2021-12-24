package com.onchall.onchall.service;

import com.onchall.onchall.entity.FileData;
import com.onchall.onchall.repository.FileDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileDataService {
    public final FileDataRepository fileDataRepository;


    public FileData findById(Long fileDataId) {
        return fileDataRepository.findById(fileDataId)
                .orElseThrow(() -> new IllegalArgumentException("파일데이터가 존재하지 않습니다."));
    }

    public FileData save(FileData fileData){
        return fileDataRepository.save(fileData);
    }
}
