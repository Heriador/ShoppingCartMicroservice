package com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCartResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private BrandResponse brand;
    private List<CategoryResponse> categories;
    private String nextSupplyDate;
    private Integer cartQuantity;
    private BigDecimal cartPrice;
}
