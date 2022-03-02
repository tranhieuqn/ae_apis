package com.ae.apis.controller;

import com.ae.apis.config.error.ResponseBuilder;
import com.ae.apis.config.error.dto.RestResponse;
import com.ae.apis.service.meida.FileStorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/storage")
@Log4j2
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadImage")
    public RestResponse<?> uploadImage(@RequestParam("file") MultipartFile file) {
        log.info("Upload image with filename = {}", file.getOriginalFilename());

        return ResponseBuilder.build(fileStorageService.storeImage(file));
    }

    @PostMapping("/uploadFile")
    public RestResponse<?> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("Upload file with filename = {}", file.getOriginalFilename());

        return ResponseBuilder.build(fileStorageService.storeFile(file));
    }

    @PostMapping("/multipleUploadFile")
    public RestResponse<?> multipleUploadFile(@RequestParam("files") MultipartFile[] files) {
        log.info("Upload multiple with size = {}", files.length);

        return ResponseBuilder.build(fileStorageService.storeFiles(files));
    }

    @PostMapping("/deleteFile")
    public RestResponse<?> deleteFile(@RequestParam("link") String link) {
        log.info("Delete file with link = {}", link);

        fileStorageService.deleteFile(link);
        return ResponseBuilder.build();
    }
}
