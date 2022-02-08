package com.ae.apis.config.error.dto;


import com.ae.apis.config.error.ApiResponseStatus;

import java.util.Date;

public class ErrorResponse extends RestResponse<Object> {

    public static ErrorResponse build(ApiResponseStatus status, String messageDesc) {
        RestResponseHeader header = new RestResponseHeader();
        header.setRsCode(status.code);
        header.setRsDesc(status.message);
        header.setRsDate(new Date());
        header.setDetails(messageDesc);

        ErrorResponse response = new ErrorResponse();
        response.setHeader(header);
        return response;
    }

    public static ErrorResponse build(ApiResponseStatus status, Object details) {
        RestResponseHeader header = new RestResponseHeader();
        header.setRsCode(status.code);
        header.setRsDesc(status.message);
        header.setRsDate(new Date());
        header.setDetails(details);

        ErrorResponse response = new ErrorResponse();
        response.setHeader(header);
        return response;
    }
}
