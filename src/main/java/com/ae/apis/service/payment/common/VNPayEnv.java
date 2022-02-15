package com.ae.apis.service.payment.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class VNPayEnv {
    private String vnpVersion;
    private String vnpTmnCode;
    private String vnpHashSecret;
    private String vnpPayUrl;
    private String vnpReturnUrl;
    private String vnpRefundEmail;
}
