package com.ae.apis.service.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SMSRequest {
    @JsonProperty(value = "ApiKey")
    private String ApiKey;
    @JsonProperty(value = "Content")
    private String Content;
    @JsonProperty(value = "Phone")
    private String Phone;
    @JsonProperty(value = "SecretKey")
    private String SecretKey;
    @JsonProperty(value = "IsUnicode")
    private String IsUnicode;
    @JsonProperty(value = "Brandname")
    private String Brandname;
    @JsonProperty(value = "SmsType")
    private String SmsType;
    @JsonProperty(value = "RequestId")
    private String RequestId;
    @JsonProperty(value = "CallbackUrl")
    private String CallbackUrl;
}
