package com.ae.apis.config;

import com.ae.apis.service.meida.common.S3StorageProperties;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

    @Autowired
    private S3StorageProperties s3properties;

    @Bean
    public AmazonS3 getS3client() {
        AWSCredentials credentials = new BasicAWSCredentials(s3properties.getAccessKeyId(), s3properties.getSecretKey());

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build();
    }
}