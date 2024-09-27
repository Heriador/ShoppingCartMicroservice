package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util;

public class PaginationConstants {

    private PaginationConstants(){

    }

    public static final String PAGE_NUMBER = "page";
    public static final String PAGE_SIZE = "size";
    public static final int MIN_PAGE_NUMBER = 0;
    public static final int MIN_PAGE_SIZE = 1;
    public static final String PAGE_NUMBER_LOWER_THAN_MIN_MESSAGE = "Page number must be greater than or equal to "+MIN_PAGE_NUMBER;
    public static final String PAGE_SIZE_LOWER_THAN_MIN_MESSAGE = "Page size must be greater than or equal to "+MIN_PAGE_SIZE;
}
