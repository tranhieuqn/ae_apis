package com.ae.apis.repository.custom.Impl;

import com.ae.apis.controller.dto.OrderResponse;
import com.ae.apis.controller.dto.OrderSimpleResponse;
import com.ae.apis.controller.query.base.QueryPredicate;
import com.ae.apis.repository.custom.CustomOrderRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class CustomOrderRepositoryImpl implements CustomOrderRepository {

    @Autowired
    private EntityManager em;

    @Override
    public Page<OrderSimpleResponse> getOrders(QueryPredicate queryPredicate) {
        return queryPredicate.fetch(buildOrderSimpleQuery(em));
    }

    @Override
    public OrderResponse findOrderById(Long id) {
        return buildOrderQuery(em)
                .where(qOrder.id.eq(id))
                .fetchOne();
    }

    @Override
    public OrderResponse findOrderByIdAndUserId(Long id, Long userId) {
        var builder = new BooleanBuilder();
        builder.and(qOrder.id.eq(id));
        builder.and(qOrder.userId.eq(userId));

        return buildOrderQuery(em)
                .where(builder)
                .fetchOne();
    }
}
