package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.mapper;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartEntity;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CartDetailsEntityMapper.class})
public interface CartEntityMapper {

        @Mapping(target = "items", ignore = true)
         CartEntity toEntity(Cart cart);

         Cart toCart(CartEntity cartEntity);
}
