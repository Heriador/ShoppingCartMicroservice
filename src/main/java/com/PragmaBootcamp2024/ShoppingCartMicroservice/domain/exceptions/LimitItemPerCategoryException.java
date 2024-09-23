package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions;

public class LimitItemPerCategoryException extends RuntimeException {
    public LimitItemPerCategoryException(String message) {
        super(message);
    }
}
