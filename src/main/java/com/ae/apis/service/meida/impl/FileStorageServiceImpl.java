package com.ae.apis.service.meida.impl;

import com.ae.apis.service.amazon.FileStorageClient;
import com.ae.apis.service.meida.FileStorageService;
import com.ae.apis.service.meida.dto.UploadFileResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
@Log4j2
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private FileStorageClient storageClient;

    @Override
    public UploadFileResponse storeFile(MultipartFile file) {
        log.info("Upload file start");
        String fileName = storageClient.generateFileName(file);
        String fileURL = storageClient.uploadFile(file, fileName);
        log.info("Upload file done");

        return new UploadFileResponse(fileName, fileURL, file.getContentType(), file.getSize());
    }

    @Override
    public UploadFileResponse storeImage(MultipartFile file) {
        log.info("Upload Image start");
        String fileName = storageClient.generateImageFileName(file, ".png");
        String fileURL = storageClient.uploadImage(file, fileName);
        log.info("Upload Image done");

        return new UploadFileResponse(fileName, fileURL, file.getContentType(), file.getSize());
    }

    @Override
    public void deleteFile(String link) {
        log.info("Delete file start");
        storageClient.deleteFile(link);
        log.info("Delete file");
    }

    @Override
    public List<?> storeFiles(MultipartFile[] files) {
        log.info("Upload file start");
        List<HashMap<String, Object>> result = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = storageClient.generateFileName(file);
            String fileURL = storageClient.uploadImage(file, fileName);

            HashMap<String, Object> resultItem = new HashMap<>();
            resultItem.put("fileType", file.getContentType());
            resultItem.put("fileLink", fileURL);
            resultItem.put("fileSize", file.getSize());
            resultItem.put("fileName", file.getSize());
            result.add(resultItem);
        }
        log.info("Upload file done");
        return result;
    }

}
