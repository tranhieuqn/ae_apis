package com.ae.apis.service;

import com.ae.apis.controller.dto.OrderRequest;
import com.ae.apis.controller.dto.OrderRes;
import com.ae.apis.controller.dto.OrderResponse;
import com.ae.apis.controller.dto.OrderSimpleResponse;
import com.ae.apis.controller.query.base.QueryPredicate;
import com.ae.apis.entity.Order;
import com.ae.apis.entity.Payment;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page<OrderSimpleResponse> getOrders(QueryPredicate queryPredicate);

    OrderResponse getOrder(Long id);

    Order createOrder(OrderRequest request, Payment payment);

    void updateOrder(Long id, OrderRequest request);

    OrderRes submitOrder(OrderRequest request);
}
