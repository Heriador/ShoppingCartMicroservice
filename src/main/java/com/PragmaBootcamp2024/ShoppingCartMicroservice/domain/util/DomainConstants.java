package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;

public class DomainConstants {

    public static final String ITEM_NOT_FOUND_EXCEPTION_MESSAGE = "Item not found";
    public static final String INSUFFICIENT_STOCK_MESSAGE = "Insufficient stock";
    public static final String QUANTITY_NOT_POSITIVE_MESSAGE = "Quantity must be positive";
    public static final String NEXT_SUPPLY_DATE = ", next supply date: ";


    public static final String SUPPLY_DATE_FORMAT = "EEEE, MMMM dd, yyyy";
    public static final CartDetails NULL_CART_DETAILS = null;

    private DomainConstants() {
    }
}
