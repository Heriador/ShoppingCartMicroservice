package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Item;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.PaginationCustom;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationUtil;

import java.util.List;

public interface ICartDetailsServicePort {
    void addProduct(CartDetails cartDetails);

    void deleteItem(Long itemId, Cart cart);

    PaginationCustom<Item> getItemsFromCartPaginated(List<CartDetails> cartDetails, PaginationUtil paginationUtil);
}
