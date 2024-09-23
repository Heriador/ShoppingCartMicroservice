package com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartResponse {
    private Integer quantity;
    private Long itemId;
}
