package com.ae.apis.repository.custom;

import com.ae.apis.controller.dto.OrderDetailResponse;
import com.ae.apis.controller.dto.ProductSimpleResponse;
import com.ae.apis.entity.QOrderDetail;
import com.ae.apis.entity.QProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.List;

public interface CustomOrderDetailRepository {
    List<OrderDetailResponse> findAllOrderDetailByOrderId(Long id);

    QOrderDetail orderDetail = QOrderDetail.orderDetail;
    QProduct product = QProduct.product;

    default JPAQuery<OrderDetailResponse> buildOrderDetailQuery(EntityManager em) {
        return new JPAQuery<OrderDetailResponse>(em)
                .select(buildOrderDetailBean())
                .from(orderDetail)
                .innerJoin(product).on(product.id.eq(orderDetail.product.id))
                .orderBy(orderDetail.id.asc());
    }

    default QBean<OrderDetailResponse> buildOrderDetailBean() {
        return Projections.bean(
                OrderDetailResponse.class,
                orderDetail.id,
                orderDetail.order.id.as("orderId"),
                Projections.bean(
                        ProductSimpleResponse.class,
                        product.id,
                        product.category.id.as("categoryId"),
                        product.name,
                        product.thumbnail
                ).as("product"),
                orderDetail.quantity,
                orderDetail.unitPrice
        );
    }
}
