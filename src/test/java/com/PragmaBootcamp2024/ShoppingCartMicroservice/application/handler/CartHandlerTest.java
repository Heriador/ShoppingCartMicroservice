package com.PragmaBootcamp2024.ShoppingCartMicroservice.application.handler;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.CartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.CartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.mapper.CartRequestMapper;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.mapper.CartResponseMapper;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartServicePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.factory.CartDetailsFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartHandlerTest {

    @Mock
    private ICartServicePort cartServicePort;

    @Mock
    private CartRequestMapper cartRequestMapper;

    @Mock
    private CartResponseMapper cartResponseMapper;

    @InjectMocks
    private CartHandler cartHandler;

    @Test
    @DisplayName("Add product should pass")
    void addProductShouldPass() {

        CartRequest cartRequest = CartDetailsFactory.getCartRequest();

        when(cartRequestMapper.toCart(cartRequest)).thenReturn(CartDetailsFactory.getCartDetails());
        doNothing().when(cartServicePort).addProduct(CartDetailsFactory.getCartDetails());

        CartResponse cartResponse = cartHandler.addProduct(cartRequest);

        assertEquals(cartRequest.getQuantity(), cartResponse.getQuantity());
        assertEquals(cartRequest.getItemId(), cartResponse.getItemId());

        verify(cartRequestMapper, times(1)).toCart(cartRequest);
        verify(cartServicePort, times(1)).addProduct(CartDetailsFactory.getCartDetails());

    }

    @Test
    @DisplayName("Delete item should pass")
    void deleteItemShouldPass() {

        Long itemId = 1L;

        doNothing().when(cartServicePort).deleteItem(itemId);

        cartHandler.deleteItem(itemId);

        verify(cartServicePort, times(1)).deleteItem(itemId);

    }

}