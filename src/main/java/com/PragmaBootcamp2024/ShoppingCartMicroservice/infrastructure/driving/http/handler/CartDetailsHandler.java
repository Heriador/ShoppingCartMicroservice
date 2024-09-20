package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.handler;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartDetailsServicePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.Dto.request.CartDetailsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartDetailsHandler implements ICartDetailsHandler {

    private final ICartDetailsServicePort cartDetailsServicePort;

    @Override
    public void addProduct(CartDetailsRequest cartDetailsRequest) {

        CartDetails cartDetails = new CartDetails();
        cartDetails.setItemId(cartDetailsRequest.getItemId());
        cartDetails.setQuantity(cartDetailsRequest.getQuantity());

        cartDetailsServicePort.addProduct(cartDetails.getItemId(), cartDetails);
    }
}
