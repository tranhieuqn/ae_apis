package com.ae.apis.controller.dto;

import com.ae.apis.entity.enums.ProductStatus;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
public class ProductResponse {
  private Long id;
  private Long categoryId;
  private String categoryName;
  private String name;
  private String description;
  private BigDecimal price;
  private String thumbnail;
  private ProductStatus status;
  private Long mediaId;
  private List<MediaResponse> mediaDetails;
  private List<PropertyResponse> properties;
}