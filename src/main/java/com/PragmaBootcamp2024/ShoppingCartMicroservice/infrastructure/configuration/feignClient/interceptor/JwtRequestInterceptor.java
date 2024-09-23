package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient.interceptor;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.util.FeignConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;


public class JwtRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {

        String jwt = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();


        requestTemplate.header(FeignConstants.AUTHORIZATION_HEADER,FeignConstants.TOKEN_PREFIX+jwt);
    }
}
