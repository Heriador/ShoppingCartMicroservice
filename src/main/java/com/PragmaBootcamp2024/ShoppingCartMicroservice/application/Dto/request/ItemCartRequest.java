package com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCartRequest {
    List<Long> itemIds;
}
