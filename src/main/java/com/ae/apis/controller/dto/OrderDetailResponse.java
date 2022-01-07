package com.ae.apis.controller.dto;

import java.math.BigDecimal;
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
public class OrderDetailResponse {
  private Long id;
  private Long orderId;
  private ProductSimpleResponse product;
  private int quantity;
  private BigDecimal unitPrice;
}