package com.ae.apis.config.error;

import com.ae.apis.config.error.dto.PagingItem;
import com.ae.apis.config.error.dto.RestResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class ResponseBuilder {

    public static <T> RestResponse<T> build(T body) {
        return new RestResponse<>(body);
    }

    public static <T> RestResponse<List<T>> build(Page<T> body) {
        PagingItem paging = new PagingItem();
        paging.setPageNumber(body.getPageable().getPageNumber());
        paging.setNumberOfElements(body.getNumberOfElements());
        paging.setTotalPages(body.getTotalPages());
        paging.setTotalElements(body.getTotalElements());

        RestResponse<List<T>> response = new RestResponse<>(body.getContent());
        response.setPaging(paging);
        return response;
    }
}
