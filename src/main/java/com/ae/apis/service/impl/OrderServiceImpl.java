package com.ae.apis.service.impl;

import com.ae.apis.config.error.NotFoundException;
import com.ae.apis.controller.dto.OrderDetailRequest;
import com.ae.apis.controller.dto.OrderRequest;
import com.ae.apis.controller.dto.OrderResponse;
import com.ae.apis.controller.dto.OrderSimpleResponse;
import com.ae.apis.controller.query.base.QueryPredicate;
import com.ae.apis.entity.Order;
import com.ae.apis.entity.enums.PaymentType;
import com.ae.apis.repository.OrderRepository;
import com.ae.apis.security.AuthenticationUtils;
import com.ae.apis.service.OrderDetailService;
import com.ae.apis.service.OrderService;
import com.ae.apis.service.payment.PaymentService;
import com.ae.apis.service.payment.dto.PaymentCreatedRes;
import com.ae.apis.utils.RandomCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RandomCodeGenerator randomCodeGenerator;

    @Override
    public Page<OrderSimpleResponse> getOrders(QueryPredicate queryPredicate) {
        return repository.getOrders(queryPredicate);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        boolean isRoleUser = AuthenticationUtils.isRoleUser();
        return getOrder(id, isRoleUser);
    }

    public OrderResponse getOrder(Long id, boolean isRoleUser) {
        OrderResponse orderResponse;
        if (isRoleUser) {
            Long userId = AuthenticationUtils.getUserId();
            orderResponse = repository.findOrderByIdAndUserId(id, userId);
        } else {
            orderResponse = repository.findOrderById(id);
        }

        if (orderResponse == null) {
            throw new NotFoundException(Order.class, id);
        }

        var orderDetails = orderDetailService.getOrderDetailsByOrderId(id);
        orderResponse.setOrderDetails(orderDetails);

        return orderResponse;
    }

    @Override
    @Transactional
    public void createOrder(OrderRequest request) {
        Order order = new Order();
        Long userId = AuthenticationUtils.getUserId();
        order.setUserId(userId);
        order.setRefNumber(request.getRefNumber());
        order.setStatus(request.getStatus());
        order.setNote(request.getNote());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setOrderDate(OffsetDateTime.now());

        var saved = repository.save(order);
        orderDetailService.createOrderDetails(saved, request.getOrderDetails());
    }

    @Override
    public void updateOrder(Long id, OrderRequest request) {
        var order = repository.findById(id).orElseThrow(
                () -> new NotFoundException(Order.class, id)
        );

        order.setRefNumber(request.getRefNumber());
        order.setStatus(request.getStatus());
        order.setNote(request.getNote());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setOrderDate(OffsetDateTime.now());

        orderDetailService.updateOrderDetails(order, request.getOrderDetails());
    }

    @Override
    public PaymentCreatedRes submitOrder(OrderRequest request) {
        this.createOrder(request);
        Long userId = AuthenticationUtils.getUserId();
        BigDecimal originalFee = new BigDecimal(0);
        for (OrderDetailRequest item: request.getOrderDetails()){
            originalFee = originalFee.add(BigDecimal.valueOf(item.getQuantity()).multiply(item.getUnitPrice()));
        }
        String code = randomCodeGenerator.generateCode(4,4);


        PaymentCreatedRes paymentCreatedRes = paymentService.createPayment(
                PaymentType.VNPAY, userId, originalFee.longValue(), originalFee.longValue(), code, "order"
        );

        return paymentCreatedRes;
    }

}
