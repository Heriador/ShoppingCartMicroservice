package com.PargmaBootcamp2024.ShoppingCartMicroservice.configuration.beanConfiguration;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.adapter.CartAdapter;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.mapper.CartEntityMapper;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.repository.CartRepository;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.CartUseCases;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartServicePort;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final CartRepository cartRepository;
    private final CartEntityMapper cartEntityMapper;

    @Bean
    public ICartPersistencePort cartPersistencePort(){
        return new CartAdapter();
    }

    @Bean
    public ICartServicePort cartServicePort(){
        return new CartUseCases(cartPersistencePort());
    }
}
