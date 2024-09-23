package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi;


import java.util.List;

public interface IStockPersistencePort {

    Boolean existsById(Long itemId);

    Boolean hasStock(Long itemId, Integer quantity);

    List<String> getCategoriesNameByItemId(Long itemId);
}
