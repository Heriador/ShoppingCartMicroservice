package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;

public interface ICartDetailsServicePort {
    void addProduct(CartDetails cartDetails);

    void deleteItem(Long itemId, Long id);
}
