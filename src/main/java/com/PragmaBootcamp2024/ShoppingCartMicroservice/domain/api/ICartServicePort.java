package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Item;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.PaginationCustom;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationUtil;

public interface ICartServicePort {
    void addProduct(CartDetails cartDetails);

    void deleteItem(Long itemId);

    PaginationCustom<Item> getItemsFromCartPaginated(PaginationUtil paginationUtil);
}
