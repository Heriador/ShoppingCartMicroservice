package com.PargmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "stockMicroservice", url = "${stock_service.url}", configuration = FeignConfiguration.class)
public interface IStockFeignClient {

    @GetMapping(value = "/item/id/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Object getItemById(@PathVariable Long itemId);
}
