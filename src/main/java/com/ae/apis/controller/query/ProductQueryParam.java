package com.ae.apis.controller.query;

import com.ae.apis.controller.query.base.QueryParam;
import com.ae.apis.entity.QCategory;
import com.ae.apis.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;

public class ProductQueryParam extends QueryParam<ProductFilterParam> {
    @Override
    protected Predicate buildFilterPredicate(String timezone) {
        var product = QProduct.product;
        var category = QCategory.category;
        var builder = new BooleanBuilder();
        var filter = super.getFilter();

        if (filter == null) {
            return builder;
        }

        if (filter.getIds() != null && !filter.getIds().isEmpty()) {
            builder.and(product.id.in(filter.getIds()));
        }

        if (filter.getCategoryId() != null) {
            builder.and(category.id.eq(filter.getCategoryId()));
        }

        if (StringUtils.isBlank(filter.getName())) {
            builder.and(category.name.likeIgnoreCase(filter.getName()));
        }

        return builder;
    }

    @Override
    protected OrderSpecifier<?> buildOrderClause() {
        return QCategory.category.id.desc();
    }
}
