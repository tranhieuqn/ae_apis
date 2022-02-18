package com.ae.apis.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRes {
    private String status;
    private String paymentUrl;

    public static OrderRes ofSuccess(String paymentUrl) {
        return new OrderRes("success", paymentUrl);
    }
}
