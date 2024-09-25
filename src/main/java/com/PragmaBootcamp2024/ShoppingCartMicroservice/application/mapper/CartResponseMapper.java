package com.PragmaBootcamp2024.ShoppingCartMicroservice.application.mapper;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.CartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartResponseMapper {



    CartResponse toResponse(Cart cart);

    List<CartResponse> toResponseList(List<Cart> cartList);

    CartResponse toCartResponse(CartDetails cart);
}
