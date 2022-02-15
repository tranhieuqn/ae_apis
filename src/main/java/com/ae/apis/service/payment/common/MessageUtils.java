package com.ae.apis.service.payment.common;

import com.ae.apis.config.YamlPropertySourceFactory;
import com.ae.apis.utils.SMSProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Configuration
@Validated
@PropertySource(value = "classpath:application-message.yml", factory = YamlPropertySourceFactory.class, encoding = "UTF-8")
@ConfigurationProperties("payment.vnpay")
@Primary
@Getter
@Setter
public class MessageUtils {
    private List<Message> message;

    @Data
    public static class Message {
        private String code;
        private String message;
    }

    public String getMessage(String code) {
        if (message == null) return null;

        Optional<Message> messageOpt = message.stream().filter(x -> x.getCode().equals(code)).findFirst();
        return messageOpt.map(Message::getMessage).orElse(null);
    }
}
