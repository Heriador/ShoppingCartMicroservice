package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.controller;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.Dto.request.CartRequest;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.handler.ICartHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartRestController {

    private final ICartHandler cartHandler;

    @PreAuthorize("hasRole('CLIENT")
    @PostMapping
    public void saveCart(CartRequest cartRequest) {
        cartHandler.saveCart(cartRequest);
    }

}
