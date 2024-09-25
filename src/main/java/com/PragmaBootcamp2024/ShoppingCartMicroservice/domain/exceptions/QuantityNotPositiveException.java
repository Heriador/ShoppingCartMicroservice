package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions;

public class QuantityNotPositiveException extends RuntimeException {
    public QuantityNotPositiveException(String message) {
        super(message);
    }
}
