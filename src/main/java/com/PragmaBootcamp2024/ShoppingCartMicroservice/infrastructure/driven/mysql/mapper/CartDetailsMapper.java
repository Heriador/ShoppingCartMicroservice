package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.mapper;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartDetailsEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartDetailsMapper {

    CartDetailsEntity toEntity(CartDetails cartDetails);

    CartDetails toCartDetails(CartDetailsEntity cartDetailsEntity);

    List<CartDetails> toCartDetailsList(List<CartDetailsEntity> cartDetails);
}
