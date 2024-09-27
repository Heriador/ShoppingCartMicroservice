package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.util;

public class RestControllerConstants {


    public static final String CART_ROUTE = "/cart";
    public static final String ADD_ITEM_ROUTE = "/add-product";
    public static final String DELETE_ITEM_ROUTE = "/delete-item/{itemId}";
    public static final String GET_CART_ROUTE = "/get-cart";
    public static final String HAS_ROLE_CLIENT = "hasRole('CLIENT')";

    public static final String PAGE_PARAM_DEFAULT_VALUE = "0";
    public static final String SIZE_PARAM_DEFAULT_VALUE = "10";
    public static final String ORDER_PARAM_DEFAULT_VALUE = "true";

    public static final String DELETE_ITEM_SUCCESS_MESSAGE = "Item deleted successfully";

    private RestControllerConstants(){

    }
}
