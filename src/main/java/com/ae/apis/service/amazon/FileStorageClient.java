package com.ae.apis.service.amazon;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageClient {
    String uploadFile(MultipartFile multipartFile);
    String uploadFile(MultipartFile multipartFile, String fileName);
    String uploadImage(MultipartFile multipartFile);
    String uploadImage(MultipartFile multipartFile, String fileName);
    String generateFileName(MultipartFile file);
    String generateImageFileName(MultipartFile file, String extension);
    void deleteFile(String link);
}
