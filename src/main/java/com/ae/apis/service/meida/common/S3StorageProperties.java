package com.ae.apis.service.meida.common;

import com.ae.apis.config.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@Configuration
@Validated
@PropertySource(value = "classpath:application-amazons3.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties("storage.s3")
@Primary
@Getter
@Setter
public class S3StorageProperties {
	private String accessKeyId;
	private String secretKey;
	private String bucketName;
	private String awsUrl;
	private String endPoint;
}
