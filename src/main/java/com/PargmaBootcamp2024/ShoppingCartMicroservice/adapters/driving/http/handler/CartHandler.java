package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.handler;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.Dto.request.CartRequest;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.Dto.response.CartResponse;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.mapper.CartRequestMapper;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.mapper.CartResponseMapper;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartServicePort;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartHandler implements ICartHandler {

    private final ICartServicePort cartServicePort;
    private final CartRequestMapper cartRequestMapper;
    private final CartResponseMapper cartResponseMapper;

    public CartResponse addProduct(CartRequest cartRequest) {

        Cart cart = cartRequestMapper.toCart(cartRequest);

        cartServicePort.addProduct(cart);

        return cartResponseMapper.toResponse(cart);
    }
}
