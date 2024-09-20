package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.handler;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.Dto.request.CartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.Dto.response.CartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.mapper.CartRequestMapper;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.mapper.CartResponseMapper;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartHandler implements ICartHandler {

    private final ICartServicePort cartServicePort;
    private final CartRequestMapper cartRequestMapper;
    private final CartResponseMapper cartResponseMapper;

    public CartResponse addProduct(CartRequest cartRequest) {

        CartDetails cart = cartRequestMapper.toCart(cartRequest);

        cartServicePort.addProduct(cart);

        return new CartResponse(cartRequest.getQuantity(), cartRequest.getItemId());
    }
}
