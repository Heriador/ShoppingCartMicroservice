package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;

import java.util.Optional;

public interface ICartDetailsPersistencePort {
    void addProductToCart(CartDetails cartDetails);

    Optional<CartDetails> getCartDetails(Long cartId, Long productId);
}
