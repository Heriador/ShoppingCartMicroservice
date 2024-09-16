package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.handler;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.Dto.request.CartRequest;

public interface ICartHandler {
    void saveCart(CartRequest cartRequest);
}
