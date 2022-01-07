//package com.ae.apis.service;
//
//import com.ae.apis.controller.dto.OrderRequest;
//import com.ae.apis.controller.dto.OrderResponse;
//import com.ae.apis.controller.dto.OrderSimpleResponse;
//import org.springframework.data.domain.Page;

//public interface OrderService {
//
//  Page<OrderSimpleResponse> getOrders();
//
//  Page<OrderSimpleResponse> getOrders(Long userId);
//
//  OrderResponse getOrder(Long id);
//
//  OrderResponse getOrder(Long id, Long userId);
//
//  OrderResponse createOrder(OrderRequest request);
//
//  OrderResponse updateOrder(Long id, OrderRequest request);
//
//  OrderResponse updateOrder(Long id, OrderRequest request, Long userId);
//}
