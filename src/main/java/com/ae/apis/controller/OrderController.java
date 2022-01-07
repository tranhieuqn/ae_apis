//package com.ae.apis.controller;
//
//import com.ae.apis.controller.dto.OrderRequest;
//import com.ae.apis.controller.dto.OrderResponse;
//import com.ae.apis.controller.dto.OrderSimpleResponse;
//import com.ae.apis.service.OrderService;
//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController("/orders")
//public class OrderController {
//
//  @Autowired
//  private OrderService orderService;
//
//  @GetMapping
//  public Page<OrderSimpleResponse> getOrders() {
//    return orderService.getOrders();
//  }
//
//  @GetMapping("/{id}")
//  public OrderResponse getOrder(@PathVariable Long id) {
//    return orderService.getOrder(id);
//  }
//
//  @PostMapping
//  public void createOrder(@Valid @RequestBody OrderRequest request) {
//    orderService.createOrder(request);
//  }
//
//  @PutMapping("/{id}")
//  public void updateOrder(@PathVariable Long id, @Valid @RequestBody OrderRequest request) {
//    orderService.updateOrder(id, request);
//  }
//}
