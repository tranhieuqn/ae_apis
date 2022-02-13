package com.ae.apis.controller.dto;

import com.ae.apis.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
public class OrderSimpleResponse {
  private Long id;
  private Long userId;
  private String refNumber;
  private OrderStatus status;
  private OffsetDateTime orderDate;
}