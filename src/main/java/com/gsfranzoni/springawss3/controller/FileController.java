package com.gsfranzoni.springawss3.controller;

import com.gsfranzoni.springawss3.service.AwsS3Service;
import com.gsfranzoni.springawss3.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

@Controller
@RequestMapping("/files")
@AllArgsConstructor
public class FileController {

    private final AwsS3Service awsS3Service;

    private final FileService fileService;

    private final record UploadResponse(String key) { }

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> upload(@RequestPart("file") MultipartFile multipartFile) {
        try {
            File file = this.fileService.createFileFromMultipartFile(multipartFile);
            String key = this.awsS3Service.upload(file).get();
            return ResponseEntity.ok(new UploadResponse(key));
        } catch (Throwable throwable) {
            return ResponseEntity.badRequest().body(new UploadResponse(null));
        }
    }

    @GetMapping(value = "/download/{key}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> download(@PathVariable String key) {
        try {
            byte[] bytes = this.awsS3Service.download(key).get();
            return ResponseEntity
                    .ok()
                    .body(bytes);
        } catch (Throwable throwable) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
