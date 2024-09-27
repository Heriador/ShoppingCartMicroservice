package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util;


import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.ValidationException;

import java.util.HashMap;
import java.util.Map;

public class PaginationValidator {

    private PaginationValidator(){

    }

    public static void validate(PaginationUtil paginationUtil){
        Map<String, String> errors = new HashMap<>();
        if(paginationUtil.getPage() < PaginationConstants.MIN_PAGE_NUMBER){
            errors.put(PaginationConstants.PAGE_NUMBER, PaginationConstants.PAGE_NUMBER_LOWER_THAN_MIN_MESSAGE);
        }
        if(paginationUtil.getSize() < PaginationConstants.MIN_PAGE_SIZE){
            errors.put(PaginationConstants.PAGE_SIZE, PaginationConstants.PAGE_SIZE_LOWER_THAN_MIN_MESSAGE);
        }
        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }


}
