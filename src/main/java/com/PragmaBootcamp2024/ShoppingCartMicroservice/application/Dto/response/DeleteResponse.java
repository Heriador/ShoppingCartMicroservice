package com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteResponse {
    private Long itemId;
    private String message;
}
