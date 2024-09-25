package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.adapter;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartDetailsPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartDetailsEntity;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.mapper.CartDetailsMapper;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.repository.CartDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
public class CartDetailsAdapter implements ICartDetailsPersistencePort {

    private final CartDetailsRepository cartDetailsRepository;
    private final CartDetailsMapper cartDetailsMapper;

    @Override
    public void addProductToCart(CartDetails cartDetails) {
        cartDetailsRepository.save(cartDetailsMapper.toEntity(cartDetails));
    }

    @Override
    public Optional<CartDetails> getCartDetails(Long cartId, Long productId) {

        Optional<CartDetailsEntity> cartDetails = cartDetailsRepository.findByCartIdAndItemId(cartId, productId);

        return cartDetails.map(cartDetailsMapper::toCartDetails);
    }

    @Override
    public List<Long> getItemIdsByCartId(Long cartId) {
        return cartDetailsRepository.findItemIdsByCartId(cartId);
    }

    @Override
    public void deleteItemFromCart(CartDetails cartDetails) {
        cartDetailsRepository.delete(cartDetailsMapper.toEntity(cartDetails));
    }

    @Override
    public Optional<List<CartDetails>> findByCartId(Long cartId) {

        Optional<List<CartDetailsEntity>> cartDetails = cartDetailsRepository.findByCartId(cartId);

        return cartDetails.map(cartDetailsMapper::toCartDetailsList);

    }
}
