package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.mapper;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.Dto.request.CartRequest;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "itemId", source = "itemId")
    @Mapping(target = "quantity", source = "quantity")
//    @Mapping(target = "createdAt", dateFormat = "yyyy-MM-dd", defaultValue = "Date()")
//    @Mapping(target = "updatedAt", defaultValue = "CURRENT_DATE")
    Cart toCart(CartRequest cartRequest);
}
