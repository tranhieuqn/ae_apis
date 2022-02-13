package com.ae.apis.service;

import com.ae.apis.controller.dto.OrderRequest;
import com.ae.apis.controller.dto.OrderResponse;
import com.ae.apis.controller.dto.OrderSimpleResponse;
import com.ae.apis.controller.query.base.QueryPredicate;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page<OrderSimpleResponse> getOrders(QueryPredicate queryPredicate);

    OrderResponse getOrder(Long id);

    void createOrder(OrderRequest request);

    void updateOrder(Long id, OrderRequest request);
}
