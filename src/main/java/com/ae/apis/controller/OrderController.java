package com.ae.apis.controller;

import com.ae.apis.config.error.ResponseBuilder;
import com.ae.apis.config.error.dto.EmptyResponse;
import com.ae.apis.config.error.dto.RestResponse;
import com.ae.apis.controller.dto.OrderRequest;
import com.ae.apis.controller.dto.OrderRes;
import com.ae.apis.controller.dto.OrderResponse;
import com.ae.apis.controller.dto.OrderSimpleResponse;
import com.ae.apis.controller.query.OrderQueryParam;
import com.ae.apis.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public RestResponse<?> getOrders(@NotNull OrderQueryParam queryParam) {
        log.info("Get orders");

        return ResponseBuilder.build(orderService.getOrders(queryParam.build()));
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportOrders(@NotNull OrderQueryParam queryParam, HttpServletResponse response) {
        log.info("Export");
        orderService.exportOrders(queryParam.build(), response);

        return ResponseEntity.noContent().build();
    }
    

    @GetMapping("/{id}")
    public RestResponse<?> getOrder(@PathVariable Long id) {
        log.info("Get order with id = {}", id);

        return ResponseBuilder.build(orderService.getOrder(id));
    }

    @PostMapping("/submit")
    public RestResponse<?> submitOrder(@Valid @RequestBody OrderRequest request) {
        log.info("Submit order with request = [{}]", request);
        OrderRes response = orderService.submitOrder(request);

        return ResponseBuilder.build(response);
    }

    @PutMapping("/{id}")
    public RestResponse<?> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderRequest request) {
        log.info("Update order with id {} and request = [{}]", id, request);
        orderService.updateOrder(id, request);

        return ResponseBuilder.build();
    }
}
