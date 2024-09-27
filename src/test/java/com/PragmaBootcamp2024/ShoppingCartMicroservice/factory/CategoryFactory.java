package com.PragmaBootcamp2024.ShoppingCartMicroservice.factory;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.CategoryResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Category;


public class CategoryFactory {

    private static final Category category;
    private static final CategoryResponse categoryResponse;



    static {
        category = new Category(1L, "category", "description");


        categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
    }

    public static Category getCategory(){
        return category;
    }


    public static CategoryResponse getCategoryResponse(){
        return categoryResponse;
    }

}
