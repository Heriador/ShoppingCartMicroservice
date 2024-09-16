package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.mapper;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.entity.CartEntity;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartEntityMapper {

         CartEntity toEntity(Cart cart);

         Cart toDomain(CartEntity cartEntity);
}
