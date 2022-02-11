package com.ae.apis.config.error.dto;

import lombok.Data;

@Data
public class PagingItem {

    private Integer pageNumber;

    private Integer numberOfElements;

    private Integer totalPages;

    private Long totalElements;
}
