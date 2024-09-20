package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi;


public interface IStockPersistencePort {

    Boolean existsById(Long itemId);

    Boolean hasStock(Long itemId, Integer quantity);

}
