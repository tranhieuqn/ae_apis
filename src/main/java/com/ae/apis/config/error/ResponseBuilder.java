package com.ae.apis.config.error;

import com.ae.apis.config.error.dto.RestResponse;

public class ResponseBuilder {

    public static <T> RestResponse<T> build(T body) {
        return new RestResponse<>(body);
    }
}
