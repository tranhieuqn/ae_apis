package com.ae.apis.service.external.sms.common;

import com.ae.apis.config.YamlPropertySourceFactory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Configuration
@Validated
@PropertySource(value = "classpath:application-otp.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties("otp.sms")
@Primary
@Getter
@Setter
public class SMSProperties {
    @NotBlank
    private String apiKey;
    @NotBlank
    private String secretKey;
    @NotBlank
    private String brandName;
    @NotBlank
    private String url;
    private String mockPhoneNumber;
    @NotNull
    private List<Error> error;
    @NotNull
    private Auth auth;

    @Getter
    @Setter
    @Validated
    public static class Error {
        @NotNull
        private String code;
        private String message;
    }

    @Getter
    @Setter
    @Validated
    public static class Auth {
        @NotNull
        private Long expire;
    }
}
