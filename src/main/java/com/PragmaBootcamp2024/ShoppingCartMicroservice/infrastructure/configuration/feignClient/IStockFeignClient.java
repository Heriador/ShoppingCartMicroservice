package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.util.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = FeignConstants.STOCK_MICROSERVICE, url = FeignConstants.STOCK_MICROSERVICE_URL, configuration = FeignConfiguration.class)
public interface IStockFeignClient {

    @GetMapping(value = "/item/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean existsById(@PathVariable Long itemId);

    @GetMapping(value = "/item/{itemId}/{quantity}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean hasStock(@PathVariable Long itemId, @PathVariable Integer quantity);

}
