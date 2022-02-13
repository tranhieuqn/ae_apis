package com.ae.apis.controller.query;

import com.ae.apis.controller.query.base.FilterParam;
import com.ae.apis.entity.enums.OrderStatus;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class OrderFilterParam extends FilterParam {
    private Long userId;
    private String refNumber;
    private OrderStatus status;
    private String note;
    private String phone;
    private String address;
    private OffsetDateTime orderDate;
}