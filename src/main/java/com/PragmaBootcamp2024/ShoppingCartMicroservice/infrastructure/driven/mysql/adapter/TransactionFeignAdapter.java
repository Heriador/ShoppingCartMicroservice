package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.adapter;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ITransactionPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient.ITransactionFeignClient;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class TransactionFeignAdapter implements ITransactionPersistencePort {

    private final ITransactionFeignClient transactionFeignClient;

    @Override
    public LocalDate getNextSupplyDateByItemId(Long itemId) {
        try {
            return transactionFeignClient.getNextSupplyDateByItemId(itemId);

        }
        catch (Exception e){
            return null;
        }
    }
}
