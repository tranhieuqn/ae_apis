package com.ae.apis.service.payment.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class VNPayEnv {
    @NotBlank
    private String vnpVersion;
    @NotBlank
    private String vnpTmnCode;
    @NotBlank
    private String vnpHashSecret;
    @NotBlank
    private String vnpPayUrl;
    @NotBlank
    private String vnpReturnUrl;
    private String vnpRefundEmail;
}
