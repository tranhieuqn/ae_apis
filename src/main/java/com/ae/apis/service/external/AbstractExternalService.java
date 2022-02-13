package com.ae.apis.service.external;

import com.ae.apis.config.error.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public abstract class AbstractExternalService<Request, Response> implements ExternalService<Request, Response> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractExternalService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response callPOST(Request request) throws Exception {
        return callHost(request, HttpMethod.POST);
    }

    @Override
    public Response callHost(Request request, HttpMethod httpMethod) throws Exception {
        logger.info("Sending '{}' request to URL: {}", httpMethod.name(), getHostUrl());
        try {
            HttpHeaders headers = getHttpHeader();
            HttpEntity<Request> httpEntity = new HttpEntity<>(request, headers);

            String url = getHostUrl();
            Class<Response> clazz = getResponseClazz();

            ResponseEntity<Response> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, clazz);
            if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                throw new BusinessException("Call to external service failed.");
            }

            Response response = responseEntity.getBody();
            String error = toError(response);
            if (!StringUtils.isEmpty(error)) {
                throw new BusinessException("External service returned an error.");
            }
            return response;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }

    }

    protected HttpHeaders getHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    protected abstract String toError(Response response) throws Exception;

    protected abstract String getHostUrl();

    protected abstract Class<Response> getResponseClazz();
}
