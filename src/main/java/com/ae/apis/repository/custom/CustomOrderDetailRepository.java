package com.ae.apis.repository.custom;

import com.ae.apis.controller.dto.OrderDetailResponse;
import com.ae.apis.controller.dto.ProductSimpleResponse;
import com.ae.apis.entity.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.List;

public interface CustomOrderDetailRepository {
    List<OrderDetailResponse> findAllOrderDetailByOrderId(Long id);

    QOrderDetail orderDetail = QOrderDetail.orderDetail;
    QProduct product = QProduct.product;
    QCategory category = QCategory.category;
    QProperty property = QProperty.property;

    default JPAQuery<OrderDetailResponse> buildOrderDetailQuery(EntityManager em) {
        return new JPAQuery<OrderDetailResponse>(em)
                .select(buildOrderDetailBean())
                .from(orderDetail)
                .leftJoin(property).on(property.eq(orderDetail.property))
                .innerJoin(product).on(product.id.eq(orderDetail.product.id))
                .innerJoin(category).on(product.category.eq(category))
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
                        category.id.as("categoryId"),
                        category.name.as("categoryName"),
                        product.name,
                        product.description,
                        product.price,
                        product.thumbnail,
                        product.status,
                        product.media.id.as("mediaId")
                ).as("product"),
                orderDetail.quantity,
                orderDetail.unitPrice,
                property.id.as("propertyId")
        );
    }
}
