package com.ae.apis.service.impl;

import com.ae.apis.config.error.BusinessException;
import com.ae.apis.config.error.NotFoundException;
import com.ae.apis.controller.dto.*;
import com.ae.apis.controller.query.base.QueryPredicate;
import com.ae.apis.entity.Order;
import com.ae.apis.entity.Payment;
import com.ae.apis.entity.enums.OrderStatus;
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
    public Order createOrder(OrderRequest request, Payment payment) {
        Order order = new Order();
        Long userId = AuthenticationUtils.getUserId();
        order.setUserId(userId);
        String refNumber = randomCodeGenerator.generateCode(10, 10);
        order.setRefNumber(refNumber);
        order.setStatus(OrderStatus.PAYMENT_PROCESSING);
        order.setNote(request.getNote());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setOrderDate(OffsetDateTime.now());
        order.setPaymentId(payment.getId());

        var saved = repository.save(order);
        orderDetailService.createOrderDetails(saved, request.getOrderDetails());

        return saved;
    }

    @Override
    @Transactional
    public void updateOrder(Long id, OrderRequest request) {
        var order = repository.findById(id).orElseThrow(
                () -> new NotFoundException(Order.class, id)
        );

        if (!isNextStatus(order.getStatus(), request.getStatus())) {
            throw new BusinessException("Order status change does not match.");
        }

        order.setStatus(request.getStatus());
        order.setNote(request.getNote());

        repository.save(order);
    }

    public Boolean isNextStatus(OrderStatus current, OrderStatus status) {
        if (OrderStatus.PAYMENT_PROCESSING.equals(status)
                || OrderStatus.COMPLETED.equals(current)
                || OrderStatus.CANCELED.equals(current)) {
            return false;
        }

        if (OrderStatus.PAYMENT_PROCESSING.equals(current)
                && !(OrderStatus.SUBMITTED.equals(status) || OrderStatus.CANCELED.equals(status))) {
            return false;
        }

        if (OrderStatus.SUBMITTED.equals(current)
                && !(OrderStatus.SHIPPING.equals(status) || OrderStatus.CANCELED.equals(status))) {
            return false;
        }

        if (OrderStatus.SHIPPING.equals(current)
                && !(OrderStatus.COMPLETED.equals(status) || OrderStatus.CANCELED.equals(status))) {
            return false;
        }

        return true;
    }

    @Override
    public OrderRes submitOrder(OrderRequest request) {
        Long userId = AuthenticationUtils.getUserId();

        BigDecimal originalFee = new BigDecimal(0);
        if (request.getOrderDetails() != null || !request.getOrderDetails().isEmpty()) {
            for (OrderDetailRequest item : request.getOrderDetails()) {
                originalFee = originalFee.add(BigDecimal.valueOf(item.getQuantity()).multiply(item.getUnitPrice()));
            }
        }

        String code = randomCodeGenerator.generateCode(10, 10);
        PaymentCreatedRes paymentCreatedRes = paymentService.createPayment(
                PaymentType.VNPAY, userId, originalFee.longValue(), originalFee.longValue(), code, "ORDER"
        );

        this.createOrder(request, paymentCreatedRes.getPayment());

        return OrderRes.ofSuccess(paymentCreatedRes.getPaymentUrl());
    }

}
