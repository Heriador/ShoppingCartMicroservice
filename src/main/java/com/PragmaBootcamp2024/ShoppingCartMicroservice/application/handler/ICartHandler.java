package com.PragmaBootcamp2024.ShoppingCartMicroservice.application.handler;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.CartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.CartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.DeleteResponse;

public interface ICartHandler {
    CartResponse addProduct(CartRequest cartRequest);

    void deleteItem(Long itemId);
}
