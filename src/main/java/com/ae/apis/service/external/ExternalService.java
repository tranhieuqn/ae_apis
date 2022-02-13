package com.ae.apis.service.external;

import org.springframework.http.HttpMethod;

public interface ExternalService<Request, Response> {
    Response callPOST(Request request) throws Exception;
    Response callHost(Request request, HttpMethod httpMethod) throws Exception;
}
