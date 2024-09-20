package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailsRequest {
    private Integer quantity;
    private Long itemId;
}
