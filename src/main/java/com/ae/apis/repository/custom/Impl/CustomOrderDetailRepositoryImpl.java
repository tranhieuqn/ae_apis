package com.ae.apis.repository.custom.Impl;

import com.ae.apis.controller.dto.OrderDetailResponse;
import com.ae.apis.repository.custom.CustomOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class CustomOrderDetailRepositoryImpl implements CustomOrderDetailRepository {

    @Autowired
    private EntityManager em;

    @Override
    public List<OrderDetailResponse> findAllOrderDetailByOrderId(Long id) {
        return buildOrderDetailQuery(em)
                .where(orderDetail.order.id.eq(id))
                .fetch();
    }
}
