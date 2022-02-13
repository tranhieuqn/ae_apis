package com.ae.apis.repository.custom;

import com.ae.apis.controller.dto.OrderResponse;
import com.ae.apis.controller.dto.OrderSimpleResponse;
import com.ae.apis.controller.query.base.QueryPredicate;
import com.ae.apis.entity.QOrder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;

import javax.persistence.EntityManager;

public interface CustomOrderRepository {

    Page<OrderSimpleResponse> getOrders(QueryPredicate queryPredicate);

    OrderResponse findOrderById(Long id);

    QOrder qOrder = QOrder.order;

    default JPAQuery<OrderSimpleResponse> buildOrderSimpleQuery(EntityManager em) {
        return new JPAQuery<OrderSimpleResponse>(em)
                .select(buildOrderSimpleBean())
                .from(qOrder)
                .orderBy(qOrder.id.asc());
    }

    default QBean<OrderSimpleResponse> buildOrderSimpleBean() {
        return Projections.bean(
                OrderSimpleResponse.class,
                qOrder.id,
                qOrder.userId,
                qOrder.refNumber,
                qOrder.status,
                qOrder.orderDate
        );
    }

    default JPAQuery<OrderResponse> buildOrderQuery(EntityManager em) {
        return new JPAQuery<>(em)
                .select(buildOrderBean())
                .from(qOrder);
    }


    default QBean<OrderResponse> buildOrderBean() {
        return Projections.bean(
                OrderResponse.class,
                qOrder.id,
                qOrder.userId,
                qOrder.refNumber,
                qOrder.status,
                qOrder.note,
                qOrder.phone,
                qOrder.address,
                qOrder.orderDate
        );
    }

    OrderResponse findOrderByIdAndUserId(Long id, Long userId);
}
