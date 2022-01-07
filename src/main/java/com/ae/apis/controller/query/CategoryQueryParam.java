package com.ae.apis.controller.query;

import com.ae.apis.controller.query.base.QueryParam;
import com.ae.apis.entity.QCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

public class CategoryQueryParam extends QueryParam<CategoryFilterParam> {
    @Override
    protected Predicate buildFilterPredicate(String timezone) {
        var category = QCategory.category;
        var parent = new QCategory("parent");
        var builder = new BooleanBuilder();
        var filter = super.getFilter();

        if (filter == null) {
            return builder;
        }

        if (filter.getIds() != null && !filter.getIds().isEmpty()) {
            builder.and(category.id.in(filter.getIds()));
        }

        if (filter.getParentId() != null) {
            builder.and(parent.id.eq(filter.getParentId()));
        }

        return builder;
    }

    @Override
    protected OrderSpecifier<?> buildOrderClause() {
        return QCategory.category.id.desc();
    }
}
