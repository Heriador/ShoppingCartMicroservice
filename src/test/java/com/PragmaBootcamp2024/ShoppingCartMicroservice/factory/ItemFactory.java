package com.PragmaBootcamp2024.ShoppingCartMicroservice.factory;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.ItemCartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.PaginationResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Item;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.PaginationCustom;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ItemFactory {

    private static final Item item;
    private static final ItemCartResponse itemResponse;
    private static final PaginationCustom<Item> paginationCustom;
    private static final PaginationResponse<ItemCartResponse> paginationResponse;

    static {
        item = new Item();
        item.setId(1L);
        item.setName("manzana pinto");
        item.setDescription("manzana pintosa");
        item.setPrice(BigDecimal.valueOf(18.9));
        item.setStock(10);
        item.setBrand(BrandFactory.getBrand());
        item.setCategories(List.of(CategoryFactory.getCategory()));

        Item item1 = new Item();
        item1.setId(2L);
        item1.setName("manzana pinto");
        item1.setDescription("manzana pintosa");
        item1.setPrice(BigDecimal.valueOf(18.9));
        item1.setStock(0);
        item1.setBrand(BrandFactory.getBrand());
        item1.setCategories(List.of(CategoryFactory.getCategory()));



        itemResponse = new ItemCartResponse();
        itemResponse.setId(item.getId());
        itemResponse.setName(item.getName());
        itemResponse.setPrice(item.getPrice());
        itemResponse.setStock(item.getStock());
        itemResponse.setBrand(BrandFactory.getBrandResponse());


        itemResponse.setCategories(List.of(CategoryFactory.getCategoryResponse()));

        paginationCustom = new PaginationCustom<>(List.of(item, item1), 0, 1, 1L, 1, true);

        paginationResponse = new PaginationResponse<>();
        paginationResponse.setTotalPages(paginationCustom.getTotalPages());
        paginationResponse.setTotalElements(paginationCustom.getTotalElements());
        paginationResponse.setLast(paginationCustom.isLast());
        paginationResponse.setContent(List.of(itemResponse));
        paginationCustom.setPageNumber(paginationCustom.getPageNumber());
        paginationCustom.setPageSize(paginationCustom.getPageSize());

    }

    public static Item getItem() {
        return item;
    }



    public static ItemCartResponse getItemResponse() {
        return itemResponse;
    }

    public static PaginationCustom<Item> getPaginationCustom() {
        return paginationCustom;
    }

    public static PaginationResponse<ItemCartResponse> getPaginationResponse() {
        return paginationResponse;
    }


}
