package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.handler;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.Dto.request.CartRequest;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.Dto.response.CartResponse;

public interface ICartHandler {
    CartResponse addProduct(CartRequest cartRequest);
}
