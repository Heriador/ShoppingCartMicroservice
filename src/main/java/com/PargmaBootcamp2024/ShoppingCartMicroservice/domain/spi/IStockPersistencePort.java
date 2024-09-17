package com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi;

public interface IStockPersistencePort {

    boolean existsById(Long itemId);

    boolean checkStock(Long itemId, Integer quantity);

}
