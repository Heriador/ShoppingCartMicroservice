package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.adapter;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.factory.CartFactory;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartEntity;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.mapper.CartEntityMapper;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.repository.CartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartAdapterTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartEntityMapper cartEntityMapper;

    @InjectMocks
    private CartAdapter cartAdapter;

    @Test
    @DisplayName("Add product should pass")
    void addProductShouldPass() {

        when(cartEntityMapper.toEntity(any(Cart.class))).thenReturn(CartFactory.getCartEntity());
        when(cartRepository.save(any(CartEntity.class))).thenReturn(CartFactory.getCartEntity());

        cartAdapter.addProduct(CartFactory.getCart());

        verify(cartEntityMapper, times(1)).toEntity(any(Cart.class));
        verify(cartRepository, times(1)).save(any(CartEntity.class));
    }

    @Test
    @DisplayName("Exists cart should pass and return an Optional of cart")
    void existsCartShouldPass() {
        Long userId = 1L;

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(CartFactory.getCartEntity()));
        when(cartEntityMapper.toCart(CartFactory.getCartEntity())).thenReturn(CartFactory.getCart());

        assertEquals(Optional.of(CartFactory.getCart()), cartAdapter.existsCart(userId));

        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartEntityMapper, times(1)).toCart(CartFactory.getCartEntity());
    }

    @Test
    @DisplayName("Save cart should pass")
    void saveCartShouldPass() {

        when(cartEntityMapper.toEntity(any(Cart.class))).thenReturn(CartFactory.getCartEntity());
        when(cartRepository.save(any(CartEntity.class))).thenReturn(CartFactory.getCartEntity());

        cartAdapter.saveCart(CartFactory.getCart());

        verify(cartEntityMapper, times(1)).toEntity(any(Cart.class));
        verify(cartRepository, times(1)).save(any(CartEntity.class));
    }


}