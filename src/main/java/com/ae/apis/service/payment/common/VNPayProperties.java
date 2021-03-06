package com.ae.apis.service.payment.common;

import com.ae.apis.config.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.ae.apis.common.Constants.ENVIRONMENT.PROD;

@Configuration
@Validated
@PropertySource(value = "classpath:application-vnpay.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties("payment.vnpay")
@Primary
@Getter
@Setter
public class VNPayProperties {
    @NotBlank
    private String env;
    @NotNull
    private VNPayEnv dev;
    @NotNull
    private VNPayEnv prod;

    public VNPayEnv getVNPayEnv() {
        if (PROD.equals(env)) {
            return prod;
        }
        return dev;
    }

}
