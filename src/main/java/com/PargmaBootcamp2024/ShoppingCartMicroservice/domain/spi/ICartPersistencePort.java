package com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;

public interface ICartPersistencePort {
    void addProduct(Cart cart);
}
