package com.ae.apis.config.error.dto;


import com.ae.apis.config.error.ApiResponseStatus;

import java.util.Date;


public class EmptyResponse extends RestResponse<Object> {

    public static final EmptyResponse instance = new EmptyResponse();

    private EmptyResponse() {
        RestResponseHeader header = new RestResponseHeader();
        header.setRsCode(ApiResponseStatus.SUCCESS_CODE.code);
        header.setRsDate(new Date());
        header.setRsDesc(ApiResponseStatus.SUCCESS_CODE.message);

        this.setHeader(header);
    }
}
