package com.ae.apis.service;

import com.ae.apis.controller.dto.OrderDetailRequest;
import com.ae.apis.controller.dto.OrderDetailResponse;
import com.ae.apis.entity.Order;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailResponse> getOrderDetailsByOrderId(Long id);

    void createOrderDetails(Order order, List<OrderDetailRequest> orderDetails);

    void updateOrderDetails(Order order, List<OrderDetailRequest> orderDetails);
}
