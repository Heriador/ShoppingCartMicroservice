package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.mapper;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.Dto.response.CartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartResponseMapper {



    CartResponse toResponse(Cart cart);

    List<CartResponse> toResponseList(List<Cart> cartList);

}
