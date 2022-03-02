package com.ae.apis.service.payment.common;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
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
