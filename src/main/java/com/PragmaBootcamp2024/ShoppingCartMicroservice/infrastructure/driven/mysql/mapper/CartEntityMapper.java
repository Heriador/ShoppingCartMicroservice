package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.mapper;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartEntity;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartEntityMapper {

         CartEntity toEntity(Cart cart);

         Cart toCart(CartEntity cartEntity);
}
