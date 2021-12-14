package com.onchall.onchall.controller.item;

import com.onchall.onchall.entity.FileData;
import com.onchall.onchall.service.FileDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DownloadFileController {
    public final FileDataService fileDataService;


    @Value("${store.path.file}")
    private String fileStorePath;

    @GetMapping("/attach/{fileDataId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long fileDataId) throws MalformedURLException {
        FileData fileData = fileDataService.findById(fileDataId);
        String storeFileName = fileData.getStoreName();
        String uploadFileName = fileData.getUploadName();

        UrlResource resource = new UrlResource("file:" + fileStorePath + storeFileName);

        String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
