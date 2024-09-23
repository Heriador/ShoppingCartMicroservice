package com.PragmaBootcamp2024.ShoppingCartMicroservice.factory;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.CartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.CartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartDetailsEntity;

public class CartDetailsFactory {

    private static final CartDetails cartDetails;
    private static final CartDetailsEntity cartDetailsEntity;
    private static final CartRequest cartRequest;
    private static final CartResponse cartResponse;

    static {
        cartDetails = new CartDetails();
        cartDetails.setCartId(1L);
        cartDetails.setItemId(1L);
        cartDetails.setQuantity(1);

        cartDetailsEntity = new CartDetailsEntity();
        cartDetailsEntity.setCartId(cartDetails.getCartId());
        cartDetailsEntity.setItemId(cartDetails.getItemId());
        cartDetailsEntity.setQuantity(cartDetails.getQuantity());

        cartRequest = new CartRequest(cartDetails.getQuantity(), cartDetails.getItemId());

        cartResponse = new CartResponse(cartDetails.getQuantity(), cartDetails.getItemId());
    }

    public static CartDetails getCartDetails() {
        return cartDetails;
    }

    public static CartDetailsEntity getCartDetailsEntity() {
        return cartDetailsEntity;
    }

    public static CartRequest getCartRequest() {
        return cartRequest;
    }

    public static CartResponse getCartResponse() {
        return cartResponse;
    }

}
