package com.ae.apis.service.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SMSResponse {
    @JsonProperty(value = "CodeResult")
    private String CodeResult;
    @JsonProperty(value = "CountRegenerate")
    private String CountRegenerate;
    @JsonProperty(value = "ErrorMessage")
    private String ErrorMessage;
}
