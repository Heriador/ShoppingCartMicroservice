package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.adapter;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.mapper.CartEntityMapper;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.repository.CartRepository;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartAdapter implements ICartPersistencePort {

    private final CartRepository cartRepository;
    private final CartEntityMapper cartEntityMapper;

    @Override
    public void addProduct(Cart cart) {
        cartRepository.save(cartEntityMapper.toEntity(cart));
    }
}
