package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.util.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@FeignClient(name = FeignConstants.TRANSACTION_MICROSERVICE, url = FeignConstants.TRANSACTION_MICROSERVICE_URL, configuration = FeignConfiguration.class)
public interface ITransactionFeignClient {

    @GetMapping("/transaction/supply/nextSupplyDate/{itemId}")
    LocalDate getNextSupplyDateByItemId(@PathVariable Long itemId);
}
