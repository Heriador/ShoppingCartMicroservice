package com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.api;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;

public interface ICartServicePort {
    void addProduct(Cart cart);
}
