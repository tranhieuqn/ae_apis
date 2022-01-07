package com.ae.apis.controller.dto;

import com.ae.apis.entity.enums.ProductStatus;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ProductRequest {
  private Long categoryId;
  private String name;
  private String description;
  private BigDecimal price;
  private String thumbnail;
  private ProductStatus status;
  private List<MediaRequest> mediaRequests;
}