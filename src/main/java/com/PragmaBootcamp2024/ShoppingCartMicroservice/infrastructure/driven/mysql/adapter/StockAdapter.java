package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.adapter;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IStockPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient.IStockFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StockAdapter implements IStockPersistencePort {

    private final IStockFeignClient stockFeignClient;

    @Override
    public Boolean existsById(Long itemId) {


        try {

            return stockFeignClient.existsById(itemId);

        }
        catch(FeignException e) {
            return false;
        }

    }

    @Override
    public Boolean hasStock(Long itemId, Integer quantity) {
        return stockFeignClient.hasStock(itemId, quantity);
    }
}
