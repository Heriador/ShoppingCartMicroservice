package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.util;

public class RestControllerConstants {


    public static final String CART_ROUTE = "/cart";
    public static final String ADD_PRODUCT_ROUTE = "/add-product";
    public static final String DELETE_PRODUCT_ROUTE = "/delete-item/{itemId}";
    public static final String GET_CART_ROUTE = "/get-cart";
    public static final String HAS_ROLE_CLIENT = "hasRole('CLIENT')";


    private RestControllerConstants(){

    }
}
