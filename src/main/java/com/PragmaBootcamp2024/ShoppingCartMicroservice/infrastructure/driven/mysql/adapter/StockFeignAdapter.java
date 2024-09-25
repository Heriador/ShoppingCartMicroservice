package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.adapter;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IStockPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationUtil;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient.IStockFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StockFeignAdapter implements IStockPersistencePort {

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

    @Override
    public List<String> getCategoriesNameByItemId(Long itemId) {
        try {
            return stockFeignClient.getCategoriesNameByItemId(itemId);
        }
        catch(FeignException e) {
            return List.of();
        }
    }

    @Override
    public Object getCartPagination(List<Long> itemIds, PaginationUtil paginationUtil) {
        return null;
    }
}
