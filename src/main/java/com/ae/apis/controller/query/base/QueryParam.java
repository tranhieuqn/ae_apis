package com.ae.apis.controller.query.base;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import javax.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public abstract class QueryParam<T extends FilterParam> {
    @Min(0)
    private int size = Integer.MAX_VALUE;

    @Min(0)
    private int index;

    private String sort;

    private Sort.Direction order = Sort.DEFAULT_DIRECTION;

    private T filter;

    protected abstract Predicate buildFilterPredicate(String timezone);

    protected abstract OrderSpecifier<?> buildOrderClause();

    public final QueryPredicate build(String timezone) {
        Predicate predicate = buildFilterPredicate(timezone);
        OrderSpecifier<?> order = buildOrderClause();
        return new QueryPredicate(order, predicate, getIndex(), getSize());
    }
}
