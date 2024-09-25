package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationUtil;

public interface ICartServicePort {
    void addProduct(CartDetails cartDetails);

    void deleteItem(Long itemId);

    CartDetails getCart(PaginationUtil paginationUtil);
}
