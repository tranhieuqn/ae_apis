package com.ae.apis.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;

public class AppUtils {
    public static final DecimalFormat currencyFormatter = new DecimalFormat("###,###,###");

    public static Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
                .map(ServletRequestAttributes::getRequest);
    }

    public static HttpServletResponse getCurrentHttpResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpResponse = ((ServletRequestAttributes) requestAttributes).getResponse();
        return httpResponse;
    }

    public static String toCurrencyFormat(BigDecimal amount) {
        return currencyFormatter.format(amount.longValue());
    }

    public static boolean isVietNamMobileNetworkPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("(^(\\+8492)|^(092)|^(\\+8456)|^(056)|^(\\+8458)|^(058))\\d+");
    }
}
