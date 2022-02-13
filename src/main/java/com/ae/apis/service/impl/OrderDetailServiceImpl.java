package com.ae.apis.service.impl;

import com.ae.apis.config.error.NotFoundException;
import com.ae.apis.controller.dto.OrderDetailRequest;
import com.ae.apis.controller.dto.OrderDetailResponse;
import com.ae.apis.entity.Order;
import com.ae.apis.entity.OrderDetail;
import com.ae.apis.repository.OrderDetailRepository;
import com.ae.apis.service.OrderDetailService;
import com.ae.apis.service.ProductService;
import com.ae.apis.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PropertyService propertyService;

    @Override
    public List<OrderDetailResponse> getOrderDetailsByOrderId(Long id) {
        return repository.findAllOrderDetailByOrderId(id);
    }

    @Override
    @Transactional
    public void createOrderDetails(Order order, List<OrderDetailRequest> orderDetails) {
        if (orderDetails == null || orderDetails.isEmpty()) {
            return;
        }
        var orderDetailList = new ArrayList<OrderDetail>();
        for (var item : orderDetails) {
            var orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setUnitPrice(item.getUnitPrice());

            var product = productService.findProductById(item.getProductId());
            orderDetail.setProduct(product);
            //TODO: set property
            orderDetailList.add(orderDetail);
        }

        repository.saveAll(orderDetailList);
    }

    @Override
    public void updateOrderDetails(Order order, List<OrderDetailRequest> orderDetails) {
        if (orderDetails == null || orderDetails.isEmpty()) {
            return;
        }
        var orderDetailList = new ArrayList<OrderDetail>();
        for (var item : orderDetails) {
            var orderDetail = findOrderDetailById(item.getId());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setUnitPrice(item.getUnitPrice());

            var product = productService.findProductById(item.getProductId());
            orderDetail.setProduct(product);
            //TODO: set property
            orderDetailList.add(orderDetail);
        }

        repository.saveAll(orderDetailList);
    }

    private OrderDetail findOrderDetailById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(OrderDetail.class, id)
        );
    }
}
