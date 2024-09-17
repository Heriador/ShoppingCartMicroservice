package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.adapter;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IStockPersistencePort;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient.IStockFeignClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StockAdapter implements IStockPersistencePort {

    private final IStockFeignClient stockFeignClient;

    @Override
    public boolean existsById(Long itemId) {

        Object item = stockFeignClient.getItemById(itemId);

        System.out.println(item);

        return item != null;
    }

    @Override
    public boolean checkStock(Long itemId, Integer quantity) {
        return false;
    }
}
