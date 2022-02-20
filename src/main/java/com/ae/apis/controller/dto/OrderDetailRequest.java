package com.ae.apis.controller.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class OrderDetailRequest {
  private Long id;
  private Long orderId;
  private Long productId;
  private Long propertyId;
  private int quantity;
  private BigDecimal unitPrice;
}