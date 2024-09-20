package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.handler;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.Dto.request.CartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.Dto.response.CartResponse;

public interface ICartHandler {
    CartResponse addProduct(CartRequest cartRequest);
}
