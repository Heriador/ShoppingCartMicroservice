package com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartRequest {
    private Integer quantity;
    private Long itemId;
}
