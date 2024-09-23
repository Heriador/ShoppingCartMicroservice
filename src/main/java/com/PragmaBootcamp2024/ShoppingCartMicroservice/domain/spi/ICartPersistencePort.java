package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;

import java.util.Optional;

public interface ICartPersistencePort {
    void addProduct(Cart cart);

    Optional<Cart> existsCart(Long userId);

    void saveCart(Cart cart);


}
