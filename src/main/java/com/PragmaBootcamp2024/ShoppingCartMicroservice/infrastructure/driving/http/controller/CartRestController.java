package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.controller;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.Dto.request.CartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.Dto.response.CartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.handler.ICartHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.util.RestControllerConstants.*;

@RestController
@RequestMapping(CART_ROUTE)
@RequiredArgsConstructor
public class CartRestController {

    private final ICartHandler cartHandler;

    @PreAuthorize(HAS_ROLE_CLIENT)
    @PostMapping(ADD_PRODUCT_ROUTE)
    public ResponseEntity<CartResponse> addProduct(@RequestBody CartRequest cartRequest) {
        CartResponse cartResponse = cartHandler.addProduct(cartRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }

}
