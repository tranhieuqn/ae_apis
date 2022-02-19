package com.ae.apis.controller.dto;

import com.ae.apis.entity.enums.OrderStatus;

import java.time.OffsetDateTime;
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
public class OrderResponse {
  private Long id;
  private Long userId;
  private String refNumber;
  private OrderStatus status;
  private String note;
  private String phone;
  private String address;
  private OffsetDateTime orderDate;
  private List<OrderDetailResponse> orderDetails;
}