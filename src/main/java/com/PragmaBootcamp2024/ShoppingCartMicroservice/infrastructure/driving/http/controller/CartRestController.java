package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.controller;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.CartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.CartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.DeleteResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.handler.ICartHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.util.RestControllerConstants.*;

@RestController
@RequestMapping(CART_ROUTE)
@RequiredArgsConstructor
public class CartRestController {

    private final ICartHandler cartHandler;

    @PreAuthorize(HAS_ROLE_CLIENT)
    @PostMapping(ADD_ITEM_ROUTE)
    public ResponseEntity<CartResponse> addProduct(@RequestBody CartRequest cartRequest) {
        CartResponse cartResponse = cartHandler.addProduct(cartRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }

    @PreAuthorize(HAS_ROLE_CLIENT)
    @DeleteMapping(DELETE_ITEM_ROUTE)
    public ResponseEntity<DeleteResponse> deleteItem(@PathVariable Long itemId) {
        cartHandler.deleteItem(itemId);

        return ResponseEntity.ok(new DeleteResponse(itemId, DELETE_ITEM_SUCCESS_MESSAGE));
    }

}
