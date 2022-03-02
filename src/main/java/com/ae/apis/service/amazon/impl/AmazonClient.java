package com.ae.apis.service.amazon.impl;

import com.ae.apis.config.error.BusinessException;
import com.ae.apis.service.amazon.FileStorageClient;
import com.ae.apis.service.meida.common.S3StorageProperties;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
@Log4j2
public class AmazonClient implements FileStorageClient {

    @Autowired
    private AmazonS3 s3client;

    @Autowired
    private S3StorageProperties s3properties;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = null;
        File file = null;
        try {
            file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            uploadFileTos3bucket(fileName, file);

            fileUrl = String.format("%s/%s", s3properties.getAwsUrl(), fileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("s3 upload file | name: " + multipartFile.getOriginalFilename() + " | msg: " + e.getMessage());
            throw new BusinessException("Can not upload file:" + multipartFile.getOriginalFilename() + " , error: " + e.getMessage());
        } finally {
            if (file != null) {
                file.delete();
            }
        }

        return fileUrl;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile, String fileName) {
        String fileUrl = null;
        File file = null;
        try {
            file = convertMultiPartToFile(multipartFile);
            uploadFileTos3bucket(fileName, file);

            fileUrl = String.format("%s/%s", s3properties.getAwsUrl(), fileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("s3 upload file | name: " + multipartFile.getOriginalFilename() + " | msg: " + e.getMessage());
            throw new BusinessException("Can not upload file:" + fileName + " , error: " + e.getMessage());
        } finally {
            if (file != null) {
                file.delete();
            }
        }

        return fileUrl;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) {
        String fileUrl = null;
        File file = null;
        try {
            file = convertMultiPartToFile(multipartFile);
            String fileName = generateImageFileName(multipartFile, ".png");
            uploadFileTos3bucket(fileName, file);

            fileUrl = String.format("%s/%s", s3properties.getAwsUrl(), fileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("s3 upload file | name: " + multipartFile.getOriginalFilename() + " | msg: " + e.getMessage());
            throw new BusinessException("Can not upload file:" + multipartFile.getOriginalFilename() + " , error: " + e.getMessage());
        } finally {
            if (file != null) {
                file.delete();
            }
        }

        return fileUrl;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile, String fileName) {
        String fileUrl = null;
        File file = null;
        try {
            file = convertMultiPartToFile(multipartFile);
            uploadFileTos3bucket(fileName, file);

            fileUrl = String.format("%s/%s", s3properties.getAwsUrl(), fileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("s3 upload file | name: " + multipartFile.getOriginalFilename() + " | msg: " + e.getMessage());
            throw new BusinessException("Can not upload file:" + fileName + " , error: " + e.getMessage());
        } finally {
            if (file != null) {
                file.delete();
            }
        }

        return fileUrl;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


    @Override
    public String generateFileName(MultipartFile file) {
        return String.format("%d-%s", new Date().getTime(), Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "+"));
    }

    @Override
    public String generateImageFileName(MultipartFile file, String extension) {
        return String.format("%d-%s%s",
                new Date().getTime(),
                Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "+"),
                extension
        );
    }

    @Override
    public void deleteFile(String link) {
        try {
            String key = link.split(s3properties.getAwsUrl() + "/")[1];
            s3client.deleteObject(new DeleteObjectRequest(s3properties.getBucketName(), key));
        } catch (Exception e) {
            log.error("s3 delete object | url: " + link + " | msg: " + e.getMessage());
            throw new BusinessException("Can not delete file, error: " + e.getMessage());
        }
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(
                new PutObjectRequest(
                        s3properties.getBucketName(), fileName, file
                ).withCannedAcl(CannedAccessControlList.PublicRead)
        );
    }
}
