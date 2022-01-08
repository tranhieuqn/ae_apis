package com.ae.apis.controller.query.base;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
public class QueryPredicate {
    private OrderSpecifier<?> order;
    private Predicate predicate;
    private int index;
    private int size;

    public final Sort asSpringSort() {
        Sort.Direction direction = order.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC;
        String property = order.getTarget().toString();
        return Sort.by(direction, property);
    }

    public <T> Page<T> fetch(JPAQuery<T> query) {
        query.where(predicate).orderBy(order);
        long totalCount = query.fetchCount();
        int index = getIndex();
        int size = getSize();
        List<T> result = query.offset(index * size).limit(size).fetch();
        Pageable pageable = PageRequest.of(index, size, asSpringSort());
        return new PageImpl<>(result, pageable, totalCount);
    }

    public <T> Page<T> wrapper(List<T> result) {
        long totalCount = result.size();
        int index = getIndex();
        int size = getSize();
        Pageable pageable = PageRequest.of(index, size, asSpringSort());
        return new PageImpl<>(result, pageable, totalCount);
    }
}
