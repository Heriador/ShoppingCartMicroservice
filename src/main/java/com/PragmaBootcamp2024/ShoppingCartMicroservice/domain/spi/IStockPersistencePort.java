package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi;


import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.ItemCartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Item;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.PaginationCustom;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationUtil;

import java.math.BigDecimal;
import java.util.List;

public interface IStockPersistencePort {

    Boolean existsById(Long itemId);

    Boolean hasStock(Long itemId, Integer quantity);

    List<String> getCategoriesNameByItemId(Long itemId);

    BigDecimal getPriceById(Long itemId);

    PaginationCustom<Item> getCartPagination(ItemCartRequest itemCartRequest, PaginationUtil paginationUtil);
}
