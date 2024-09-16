package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.mapper;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.Dto.request.CartRequest;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartRequestMapper {

    Cart toCart(CartRequest cartRequest);
}
