package com.ae.apis.service.payment.common;

import com.ae.apis.config.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import static com.ae.apis.common.Constants.ENVIRONMENT.PROD;

@Configuration
@Validated
@PropertySource(value = "classpath:application-vnpay.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties("payment.vnpay")
@Primary
@Getter
@Setter
public class VNPayProperties {
    private String env;
    private VNPayEnv dev;
    private VNPayEnv prod;

    public VNPayEnv getVNPayEnv() {
        if (PROD.equals(env)) {
            return prod;
        }
        return dev;
    }

}
