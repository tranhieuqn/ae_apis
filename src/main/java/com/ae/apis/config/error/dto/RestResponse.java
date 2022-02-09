package com.ae.apis.config.error.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class RestResponse<RestResponseBody> {

    private RestResponseHeader header;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RestResponseBody body;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PagingItem paging;

    public RestResponse() {
    }

    public RestResponse(RestResponseBody body) {
        this.body = body;
    }
}
