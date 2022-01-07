package com.ae.apis.controller.dto;

import com.ae.apis.entity.enums.OrderStatus;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class OrderRequest {
  private Long id;
  private String refNumber;
  private OrderStatus status;
  private String note;
  private String phone;
  private String address;
  private List<OrderDetailRequest> orderDetails;
}