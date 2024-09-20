package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi;

import java.time.LocalDate;

public interface ITransactionPersistencePort {
    LocalDate getNextSupplyDateByItemId(Long itemId);
}
