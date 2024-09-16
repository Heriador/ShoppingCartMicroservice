package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.handler;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.Dto.request.CartRequest;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.mapper.CartRequestMapper;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartHandler implements ICartHandler {

    private final ICartServicePort cartServicePort;
    private final CartRequestMapper cartRequestMapper;

    public void saveCart(CartRequest cartRequest) {
        cartServicePort.saveCart(cartRequestMapper.toCart(cartRequest));
    }
}
