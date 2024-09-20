package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.util;

public class FeignConstants {

    public static final String STOCK_MICROSERVICE = "stockMicroservice";
    public static final String STOCK_MICROSERVICE_URL = "${stock_service.url}";

    public static final String TRANSACTION_MICROSERVICE = "transactionMicroservice";
    public static final String TRANSACTION_MICROSERVICE_URL = "${transaction_service.url}";

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private FeignConstants() {
    }
}
