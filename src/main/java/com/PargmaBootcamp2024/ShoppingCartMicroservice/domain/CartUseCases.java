package com.PargmaBootcamp2024.ShoppingCartMicroservice.domain;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartServicePort;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartPersistencePort;

public class CartUseCases implements ICartServicePort {

    private final ICartPersistencePort cartPersistencePort;

    public CartUseCases(ICartPersistencePort cartPersistencePort) {
        this.cartPersistencePort = cartPersistencePort;
    }

    @Override
    public void saveCart(Cart cart) {

    }
}
