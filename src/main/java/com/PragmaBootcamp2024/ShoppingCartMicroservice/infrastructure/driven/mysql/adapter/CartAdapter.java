package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.adapter;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartEntity;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.mapper.CartEntityMapper;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.repository.CartRepository;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CartAdapter implements ICartPersistencePort {

    private final CartRepository cartRepository;
    private final CartEntityMapper cartEntityMapper;

    @Override
    public void addProduct(Cart cart) {
        cartRepository.save(cartEntityMapper.toEntity(cart));
    }

    @Override
    public Optional<Cart> existsCart(Long userId) {
        Optional<CartEntity> cartEntity = cartRepository.findByUserId(userId);


        return cartEntity.map(cartEntityMapper::toCart);
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cartEntityMapper.toEntity(cart));
    }
}
