package com.ae.apis.controller.dto;

import com.ae.apis.entity.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
public class ProductSimpleResponse {
  private Long id;
  private Long categoryId;
  private String categoryName;
  private String name;
  private String description;
  private BigDecimal price;
  private String thumbnail;
  private ProductStatus status;
  private Long mediaId;
}