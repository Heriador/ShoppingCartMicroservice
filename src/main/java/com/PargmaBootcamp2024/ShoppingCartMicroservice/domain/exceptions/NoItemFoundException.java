package com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions;

public class NoItemFoundException extends RuntimeException {
    public NoItemFoundException(String message) {
        super(message);
    }
}
