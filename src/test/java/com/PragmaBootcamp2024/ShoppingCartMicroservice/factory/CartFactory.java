package com.PragmaBootcamp2024.ShoppingCartMicroservice.factory;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartEntity;

import java.time.LocalDateTime;

public class CartFactory {
    private static final Cart cart;
    private static final CartEntity cartEntity;

    static {
        cart = new Cart();
        cart.setId(1L);
        cart.setUserId(1L);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());

        cartEntity = new CartEntity();
        cartEntity.setId(cart.getId());
        cartEntity.setUserId(cart.getUserId());
        cartEntity.setCreatedAt(cart.getCreatedAt());
        cartEntity.setUpdatedAt(cart.getUpdatedAt());
    }

    public static Cart getCart() {
        return cart;
    }

    public static CartEntity getCartEntity() {
        return cartEntity;
    }
}
