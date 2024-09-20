package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
