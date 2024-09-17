package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driving.http.Dto.response;

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
