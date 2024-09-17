package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.controller;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.Dto.request.CartRequest;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.Dto.response.CartResponse;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.handler.ICartHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.util.RestControllerConstants.*;

@RestController
@RequestMapping(CART_ROUTE)
@RequiredArgsConstructor
public class CartRestController {

    private final ICartHandler cartHandler;

    @PreAuthorize(HAS_ROLE_CLIENT)
    @PostMapping(ADD_PRODUCT_ROUTE)
    public ResponseEntity<CartResponse> saveCart(@RequestBody CartRequest cartRequest) {
        CartResponse cartResponse = cartHandler.addProduct(cartRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }

}
