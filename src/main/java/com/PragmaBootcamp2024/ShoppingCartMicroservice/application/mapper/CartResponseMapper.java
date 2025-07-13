package com.PragmaBootcamp2024.ShoppingCartMicroservice.application.mapper;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.CartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.*;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Item;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.PaginationCustom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CartResponseMapper {



    CartResponse toCartResponse(CartRequest cartRequest);

    PaginationResponse<ItemCartResponse> toPaginationResponse(PaginationCustom<Item> paginationCustom);

    @Mapping(target = "cartId", source = "cart.id")
    CartDetailsResponse toCartDetailsResponse(CartDetails cartDetails);

    List<CartDetailsResponse> toCartDetailsResponseList(List<CartDetails> cartDetails);

}
