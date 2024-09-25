package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.util;

public class FeignConstants {

    public static final String STOCK_MICROSERVICE = "stockMicroservice";
    public static final String STOCK_MICROSERVICE_URL = "${stock_service.url}";

    public static final String TRANSACTION_MICROSERVICE = "transactionMicroservice";
    public static final String TRANSACTION_MICROSERVICE_URL = "${transaction_service.url}";

    public static final String EXISTS_ITEM_BY_ID_ROUTE = "/item/{itemId}";
    public static final String HAS_STOCK_ROUTE = "/item/{itemId}/{quantity}";
    public static final String GET_CATEGORIES_NAME_BY_ITEM_ID_ROUTE = "/item/categories/{itemId}";

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String GET_ITEMS_PAGINATED_BY_ID_ROUTE = "/item/items-cart";
    public static final String GET_PRICE_BY_ID_ROUTE = "/item/price/{itemId}";

    private FeignConstants() {
    }
}
