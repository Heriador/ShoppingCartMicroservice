package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;

public interface ICartServicePort {
    void addProduct(CartDetails cartDetails);
}
