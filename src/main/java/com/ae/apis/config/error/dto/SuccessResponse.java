package com.ae.apis.config.error.dto;


import com.ae.apis.config.error.ApiResponseStatus;

import java.util.Date;

public class SuccessResponse extends RestResponse<Object> {
    public static SuccessResponse build(Object body) {
        RestResponseHeader header = new RestResponseHeader();
        header.setRsCode(ApiResponseStatus.SUCCESS_CODE.code);
        header.setRsDate(new Date());
        header.setRsDesc(ApiResponseStatus.SUCCESS_CODE.message);

        SuccessResponse response = new SuccessResponse();
        response.setHeader(header);
        response.setBody(body);

        return response;
    }
}

