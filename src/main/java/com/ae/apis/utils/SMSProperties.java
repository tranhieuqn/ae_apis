package com.ae.apis.utils;

import com.ae.apis.config.YamlPropertySourceFactory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Configuration
@Validated
@PropertySource(value = "classpath:application-otp.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties("otp.sms")
@Primary
@Getter
@Setter
public class SMSProperties {
    private String apiKey;
    private String secretKey;
    private String brandName;
    private String url;
    private String mockPhoneNumber;
    private List<Error> error;

    @Data
    public static class Error {
        private String code;
        private String message;
    }
}
