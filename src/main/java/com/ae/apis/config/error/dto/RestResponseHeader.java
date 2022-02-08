package com.ae.apis.config.error.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RestResponseHeader {
    private Date rsDate;
    private String rsCode;
    private String rsDesc;
    private Object details;
}
