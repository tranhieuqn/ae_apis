package com.ae.apis.security.common;

import com.ae.apis.config.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Configuration
@Validated
@PropertySource(value = "classpath:application-jwt.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties("security.jwt")
@Primary
@Getter
@Setter
public class SecurityJwtProperties {
    @NotBlank
    private String secret;
    @NotNull
    private Long expire;
}
