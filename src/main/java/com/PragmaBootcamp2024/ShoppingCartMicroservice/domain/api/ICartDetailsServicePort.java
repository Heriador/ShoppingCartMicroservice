package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.PaginationCustom;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationUtil;

public interface ICartDetailsServicePort {
    void addProduct(CartDetails cartDetails);

    void deleteItem(Long itemId, Long id);

    PaginationCustom<CartDetails> getCart(Long id, PaginationUtil paginationUtil);
}
