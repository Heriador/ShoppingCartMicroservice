package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.ItemCartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Item;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.PaginationCustom;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.util.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;


@FeignClient(name = FeignConstants.STOCK_MICROSERVICE, url = FeignConstants.STOCK_MICROSERVICE_URL, configuration = FeignConfiguration.class)
public interface IStockFeignClient {

    @GetMapping(value = FeignConstants.EXISTS_ITEM_BY_ID_ROUTE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean existsById(@PathVariable Long itemId);

    @GetMapping(value = FeignConstants.HAS_STOCK_ROUTE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean hasStock(@PathVariable Long itemId, @PathVariable Integer quantity);

    @GetMapping(FeignConstants.GET_CATEGORIES_NAME_BY_ITEM_ID_ROUTE)
    List<String> getCategoriesNameByItemId(@PathVariable Long itemId);

    @GetMapping(value=FeignConstants.GET_ITEMS_PAGINATED_BY_ID_ROUTE, consumes = MediaType.APPLICATION_JSON_VALUE)
    PaginationCustom<Item> getItemsPaginatedById(@RequestParam Integer page,
                                                 @RequestParam Integer size,
                                                 @RequestParam Boolean ord,
                                                 @RequestParam String filterByCategoryName,
                                                 @RequestParam String filterByBrandName,
                                                 @RequestBody ItemCartRequest itemCartRequest);

    @GetMapping(FeignConstants.GET_PRICE_BY_ID_ROUTE)
    BigDecimal getPriceById(@RequestParam Long itemId);
}
