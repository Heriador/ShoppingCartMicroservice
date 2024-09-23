package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.adapter;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.factory.CartDetailsFactory;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.mapper.CartDetailsMapper;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.repository.CartDetailsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartDetailsAdapterTest {

    @Mock
    private CartDetailsRepository cartDetailsRepository;

    @Mock
    private CartDetailsMapper cartDetailsMapper;

    @InjectMocks
    private CartDetailsAdapter cartDetailsAdapter;

    @Test
    @DisplayName("Add product to cart should pass")
    void addProductToCartShouldPass() {

        when(cartDetailsMapper.toEntity(any(CartDetails.class))).thenReturn(CartDetailsFactory.getCartDetailsEntity());
        when(cartDetailsRepository.save(CartDetailsFactory.getCartDetailsEntity())).thenReturn(CartDetailsFactory.getCartDetailsEntity());


        cartDetailsAdapter.addProductToCart(CartDetailsFactory.getCartDetails());

        verify(cartDetailsMapper, times(1)).toEntity(CartDetailsFactory.getCartDetails());
        verify(cartDetailsRepository, times(1)).save(CartDetailsFactory.getCartDetailsEntity());

    }

    @Test
    @DisplayName("Get cart details should pass")
    void getCartDetailsShouldPass() {
        when(cartDetailsRepository.findByCartIdAndItemId(anyLong(), anyLong())).thenReturn(java.util.Optional.of(CartDetailsFactory.getCartDetailsEntity()));
        when(cartDetailsMapper.toCartDetails(CartDetailsFactory.getCartDetailsEntity())).thenReturn(CartDetailsFactory.getCartDetails());

        assertEquals(CartDetailsFactory.getCartDetails(), cartDetailsAdapter.getCartDetails(1L, 1L).get());

        verify(cartDetailsRepository, times(1)).findByCartIdAndItemId(1L, 1L);
        verify(cartDetailsMapper, times(1)).toCartDetails(CartDetailsFactory.getCartDetailsEntity());
    }

    @Test
    @DisplayName("Get item ids by cart id should pass")
    void getItemIdsByCartIdShouldPass() {

        List<Long> itemIds = List.of(1L, 2L, 3L);

        when(cartDetailsRepository.findItemIdsByCartId(anyLong())).thenReturn(itemIds);

        assertEquals(itemIds, cartDetailsAdapter.getItemIdsByCartId(1L));

        verify(cartDetailsRepository, times(1)).findItemIdsByCartId(1L);
    }

}