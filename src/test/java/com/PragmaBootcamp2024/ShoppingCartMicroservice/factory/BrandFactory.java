package com.PragmaBootcamp2024.ShoppingCartMicroservice.factory;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.BrandResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Brand;


public class BrandFactory {

    private static final Brand brand;
    private static final BrandResponse brandResponse;



    static {
        brand = new Brand();
        brand.setId(1L);
        brand.setName("brand");
        brand.setDescription("description");


        brandResponse = new BrandResponse();
        brandResponse.setId(brand.getId());
        brandResponse.setName(brand.getName());



    }

    public static Brand getBrand(){
        return brand;
    }


    public static BrandResponse getBrandResponse(){
        return brandResponse;
    }


}
