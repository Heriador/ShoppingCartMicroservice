package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.mapper;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartDetailsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartDetailsEntityMapper {

    CartDetailsEntity toEntity(CartDetails cartDetails);

    @Mapping(target = "cart", ignore = true)
    CartDetails toCartDetails(CartDetailsEntity cartDetailsEntity);

    @Mapping(target = "cart", ignore = true)
    List<CartDetails> toCartDetailsList(List<CartDetailsEntity> cartDetails);
}
