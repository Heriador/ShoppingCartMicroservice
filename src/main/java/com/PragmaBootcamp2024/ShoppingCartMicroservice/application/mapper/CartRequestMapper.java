package com.PragmaBootcamp2024.ShoppingCartMicroservice.application.mapper;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.CartRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cartId", ignore = true)
    @Mapping(target = "itemId", source = "itemId")
    @Mapping(target = "quantity", source = "quantity")
    CartDetails toCart(CartRequest cartRequest);
}
