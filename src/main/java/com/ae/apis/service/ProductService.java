package com.ae.apis.service;

import com.ae.apis.controller.dto.ProductRequest;
import com.ae.apis.controller.dto.ProductResponse;
import com.ae.apis.controller.dto.ProductSimpleResponse;
import com.ae.apis.controller.query.base.QueryPredicate;
import com.ae.apis.entity.QCategory;
import com.ae.apis.entity.QProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;

public interface ProductService {
  Page<ProductSimpleResponse> getProducts(QueryPredicate queryPredicate);

  ProductResponse getProduct(Long id);

  void createProduct(ProductRequest request);

  void updateProduct(Long id, ProductRequest request);

  QProduct product = QProduct.product;
  QCategory category = QCategory.category;

  default JPAQuery<ProductSimpleResponse> buildProductSimpleQuery(EntityManager em) {
    return new JPAQuery<>(em)
        .select(buildProductSimpleResBean())
        .from(product)
        .innerJoin(category).on(product.category.eq(category));
  }

  default QBean<ProductSimpleResponse> buildProductSimpleResBean() {
    return Projections.bean(
        ProductSimpleResponse.class,
        product.id,
        category.id.as("categoryId"),
        product.name,
        product.thumbnail
    );
  }

  default JPAQuery<ProductResponse> buildProductQuery(EntityManager em) {
    return new JPAQuery<>(em)
        .select(buildProductResBean())
        .from(product)
        .innerJoin(category).on(product.category.eq(category));
  }

  default QBean<ProductResponse> buildProductResBean() {
    return Projections.bean(
        ProductResponse.class,
        product.id,
        category.id.as("categoryId"),
        category.name.as("categoryName"),
        product.name,
        product.description,
        product.price,
        product.thumbnail,
        product.status,
        product.media.id.as("mediaId")
    );
  }
}
