package com.ae.apis.controller.query;

import com.ae.apis.controller.query.base.QueryParam;
import com.ae.apis.entity.QOrder;
import com.ae.apis.security.AuthenticationUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

public class OrderQueryParam extends QueryParam<OrderFilterParam> {

    @Override
    protected Predicate buildFilterPredicate(String timezone) {
        var qOrder = QOrder.order;
        var builder = new BooleanBuilder();
        var filter = super.getFilter();

        boolean isRoleUser = AuthenticationUtils.isRoleUser();
        if(isRoleUser){
            Long userId = AuthenticationUtils.getUserId();
            builder.and(qOrder.userId.eq(userId));
        }

        if (filter == null) {
            return builder;
        }

        if (filter.getIds() != null && !filter.getIds().isEmpty()) {
            builder.and(qOrder.id.in(filter.getIds()));
        }

        if(filter.getAddress() != null && !filter.getAddress().isEmpty()){
            builder.and(qOrder.address.containsIgnoreCase(filter.getAddress()));
        }

        if(filter.getPhone() != null && !filter.getPhone().isEmpty()){
            builder.and(qOrder.phone.containsIgnoreCase(filter.getPhone()));
        }

        if(filter.getRefNumber() != null && !filter.getRefNumber().isEmpty()){
            builder.and(qOrder.refNumber.containsIgnoreCase(filter.getRefNumber()));
        }

        return builder;
    }

    @Override
    protected OrderSpecifier<?> buildOrderClause() {
        return QOrder.order.id.asc();
    }
}
