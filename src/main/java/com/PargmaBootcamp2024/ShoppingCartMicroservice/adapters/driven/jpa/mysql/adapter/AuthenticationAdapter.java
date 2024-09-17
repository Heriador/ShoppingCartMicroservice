package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.adapter;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IAuthenticationPersistencePort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationAdapter implements IAuthenticationPersistencePort {

    @Override
    public Long getAuthenticatedUserId() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return Long.parseLong(userDetails.getUsername());
    }
}
