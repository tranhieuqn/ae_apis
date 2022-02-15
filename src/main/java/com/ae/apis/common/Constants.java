package com.ae.apis.common;

public class Constants {
    public final static String TOKEN_PREFIX = "Bearer ";

    public static final class VNPAY_COMMAND {
        public static final String REFUND = "refund";
        public static final String PAY = "pay";
    }

    public static final class ENVIRONMENT {
        public static final String DEV = "dev";
        public static final String PROD = "prod";
    }

    public static final class VNPAY_REFUND_TYPE {
        public static final String ALL_AMOUNT= "02";
        public static final String HAFT_AMOUNT = "03";
    }
}
