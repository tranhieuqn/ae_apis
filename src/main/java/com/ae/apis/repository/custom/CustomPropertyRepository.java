package com.ae.apis.repository.custom;

import com.ae.apis.controller.dto.PropertyResponse;
import com.ae.apis.controller.dto.PropertySimpleResponse;
import com.ae.apis.entity.QMedia;
import com.ae.apis.entity.QMediaDetail;
import com.ae.apis.entity.QProduct;
import com.ae.apis.entity.QProperty;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.List;

public interface CustomPropertyRepository {

    List<PropertySimpleResponse> getProperties(Long productId);

    PropertyResponse getProperty(Long id);

    QProduct qProduct = QProduct.product;
    QProperty qProperty = QProperty.property;
    QMedia qMedia = QMedia.media;

    default JPAQuery<PropertySimpleResponse> buildPropertySimpleResponseQuery(EntityManager em) {
        return new JPAQuery<PropertySimpleResponse>(em)
                .select(buildPropertySimpleResponseBean())
                .from(qProperty)
                .innerJoin(qProduct).on(qProduct.id.eq(qProperty.product.id))
                .orderBy(qProperty.id.asc());
    }

    default QBean<PropertySimpleResponse> buildPropertySimpleResponseBean() {
        return Projections.bean(
                PropertySimpleResponse.class,
                qProperty.id,
                qProduct.id.as("productId"),
                qProperty.name,
                qProperty.thumbnail
        );
    }

    default JPAQuery<PropertyResponse> buildPropertyResponseQuery(EntityManager em) {
        return new JPAQuery<PropertyResponse>(em)
                .select(buildPropertyResponseBean())
                .from(qProperty)
                .innerJoin(qProduct).on(qProduct.id.eq(qProperty.product.id))
                .innerJoin(qMedia).on(qMedia.id.eq(qProperty.media.id))
                .orderBy(qProperty.id.asc());
    }


    default QBean<PropertyResponse> buildPropertyResponseBean() {
        return Projections.bean(
                PropertyResponse.class,
                qProperty.id,
                qProduct.id.as("productId"),
                qProperty.name,
                qProperty.description,
                qProperty.thumbnail,
                qProperty.status,
                qMedia.id.as("mediaId")
        );
    }

    List<PropertyResponse> getPropertiesByProductId(Long productId);
}
