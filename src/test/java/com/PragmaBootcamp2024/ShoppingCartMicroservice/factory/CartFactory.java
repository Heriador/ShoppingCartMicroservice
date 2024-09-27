package com.PragmaBootcamp2024.ShoppingCartMicroservice.factory;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartEntity;

import java.time.LocalDateTime;
import java.util.List;

public class CartFactory {
    private static final Cart cart;
    private static final CartEntity cartEntity;

    static {
        cart = new Cart();
        cart.setId(1L);
        cart.setUserId(1L);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        cart.setItems(List.of(CartDetailsFactory.getCartDetails()));

        cartEntity = new CartEntity();
        cartEntity.setId(cart.getId());
        cartEntity.setUserId(cart.getUserId());
        cartEntity.setCreatedAt(cart.getCreatedAt());
        cartEntity.setUpdatedAt(cart.getUpdatedAt());
        cartEntity.setItems(List.of(CartDetailsFactory.getCartDetailsEntity()));
    }

    public static Cart getCart() {
        return cart;
    }

    public static CartEntity getCartEntity() {
        return cartEntity;
    }
}
