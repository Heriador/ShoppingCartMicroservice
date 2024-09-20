package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.handler;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.Dto.request.CartDetailsRequest;

public interface ICartDetailsHandler {
    void addProduct(CartDetailsRequest cartDetailsRequest);
}
