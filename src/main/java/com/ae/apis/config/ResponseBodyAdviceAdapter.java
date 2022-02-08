package com.ae.apis.config;


import com.ae.apis.config.error.dto.RestResponse;
import com.ae.apis.config.error.dto.RestResponseHeader;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Date;

import static com.ae.apis.config.error.ApiResponseStatus.SUCCESS_CODE;


@RestControllerAdvice
public class ResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter,
                            @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object response,
                                  @NonNull MethodParameter methodParameter,
                                  @NonNull MediaType mediaType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> aClass,
                                  @NonNull ServerHttpRequest serverHttpRequest,
                                  @NonNull ServerHttpResponse serverHttpResponse) {

        if (serverHttpRequest instanceof ServletServerHttpRequest
                && serverHttpResponse instanceof ServletServerHttpResponse) {

            if (response instanceof RestResponse) {
                if (((RestResponse<?>) response).getHeader() == null) {
                    RestResponseHeader header = new RestResponseHeader();
                    header.setRsCode(SUCCESS_CODE.code);
                    header.setRsDate(new Date());
                    header.setRsDesc(SUCCESS_CODE.message);

                    ((RestResponse<?>) response).setHeader(header);

                }
            }
        }

        return response;
    }
}