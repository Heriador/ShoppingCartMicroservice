package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.util;

public class DocumentationConstants {


    public static final String CODE_STATUS_200 = "200";
    public static final String CODE_STATUS_201 = "201";
    public static final String CODE_STATUS_400 = "400";
    public static final String CODE_STATUS_401 = "401";
    public static final String CODE_STATUS_403 = "403";
    public static final String CODE_STATUS_404 = "404";

    public static final String DESCRIPTION_STATUS_200 = "Success";
    public static final String DESCRIPTION_STATUS_201 = "Item added to cart";
    public static final String DESCRIPTION_STATUS_400 = "Bad request";
    public static final String DESCRIPTION_STATUS_401 = "Unauthorized";
    public static final String DESCRIPTION_STATUS_403 = "Forbidden";
    public static final String DESCRIPTION_STATUS_404 = "Item not found";

    public static final String CART_CONTROLLER_TAG = "Cart Controller";
    public static final String CART_CONTROLLER_DESCRIPTION = "Endpoints for cart management";
    public static final String ADD_ITEM_TO_CART_OPERATION_DESCRIPTION = "Add a item to the cart";
    public static final String DELETE_ITEM_FROM_CART_OPERATION_DESCRIPTION = "Delete a item from the cart";
    public static final String GET_ITEMS_FROM_CART_PAGINATED_OPERATION_DESCRIPTION = "Get items from the cart paginated";


    private DocumentationConstants(){
    }
}
