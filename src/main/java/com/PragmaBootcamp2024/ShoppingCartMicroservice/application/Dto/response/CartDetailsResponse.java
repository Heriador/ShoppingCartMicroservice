package com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDetailsResponse {
    private Long id;
    private Long cartId;
    private Long itemId;
    private Integer quantity;

}
