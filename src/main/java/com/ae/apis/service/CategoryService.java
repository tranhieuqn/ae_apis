package com.ae.apis.service;

import com.ae.apis.controller.dto.CategoryRequest;
import com.ae.apis.controller.dto.CategoryResponse;
import com.ae.apis.controller.dto.CategorySimpleResponse;
import com.ae.apis.controller.query.base.QueryPredicate;
import com.ae.apis.entity.QCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;

public interface CategoryService {

  Page<CategorySimpleResponse> getCategories(QueryPredicate queryPredicate);

  List<CategorySimpleResponse> getCategories(Long parentId);

  CategoryResponse getCategory(Long id);

  void createCategory(CategoryRequest request);

  void updateCategory(Long id, CategoryRequest request);

  QCategory category = QCategory.category;
  QCategory parent = new QCategory("parent");

  default JPAQuery<CategorySimpleResponse> buildCategorySimpleQuery(EntityManager em) {
    return new JPAQuery<CategorySimpleResponse>(em)
        .select(buildCategorySimpleBean())
        .from(category)
        .leftJoin(parent).on(category.parent.eq(parent))
        .orderBy(category.id.asc());
  }

  default QBean<CategorySimpleResponse> buildCategorySimpleBean() {
    var parent = new QCategory("parent");
    return Projections.bean(
        CategorySimpleResponse.class,
        category.id,
        parent.id.as("parentId"),
        category.name,
        category.thumbnail
    );
  }

  default JPAQuery<CategoryResponse> buildCategoryResQuery(EntityManager em) {
    return new JPAQuery<>(em)
        .select(buildCategoryResBean())
        .from(category);
  }

  default QBean<CategoryResponse> buildCategoryResBean() {
    return Projections.bean(
        CategoryResponse.class,
        category.id,
        category.parent.id.as("parentId"),
        category.name,
        category.thumbnail,
        category.description,
        category.status,
        category.media.id.as("mediaId")
    );
  }
}
