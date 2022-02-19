package com.ae.apis.controller;

import com.ae.apis.config.error.ResponseBuilder;
import com.ae.apis.config.error.dto.RestResponse;
import com.ae.apis.service.payment.VnpPaymentService;
import com.ae.apis.service.payment.dto.CheckPaymentProcessResponse;
import com.ae.apis.service.payment.dto.PaymentIPNProcessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/payment/vnp")
public class VnpPaymentRestController {
    private static final Logger logger = LoggerFactory.getLogger(VnpPaymentRestController.class);

    @Autowired
    private VnpPaymentService vnpPaymentService;

    @GetMapping("/ipn")
    public RestResponse<?> paymentIPNProcess(@RequestParam Map<String,String> requestParams) {
        logger.info("PaymentController::paymentIPNProcess");
        PaymentIPNProcessResponse paymentResponse = vnpPaymentService.paymentIPNProcess(requestParams);
        return ResponseBuilder.build(paymentResponse);
    }

    @GetMapping("/return")
    public RestResponse<?> checkPaymentProcess(@RequestParam Map<String,String> requestParams) {
        logger.info("MobileVnpPaymentRestController::checkPaymentProcess");
        CheckPaymentProcessResponse paymentResponse = vnpPaymentService.checkPaymentProcess(requestParams);
        return ResponseBuilder.build(paymentResponse);
    }

}
