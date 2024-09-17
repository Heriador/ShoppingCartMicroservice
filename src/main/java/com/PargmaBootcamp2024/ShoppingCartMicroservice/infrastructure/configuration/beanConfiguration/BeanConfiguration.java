package com.PargmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.beanConfiguration;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.adapter.AuthenticationAdapter;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.adapter.CartAdapter;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.adapter.StockAdapter;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.mapper.CartEntityMapper;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.repository.CartRepository;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IStockPersistencePort;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.usecases.CartUseCases;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartServicePort;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IAuthenticationPersistencePort;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartPersistencePort;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient.IStockFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final CartRepository cartRepository;
    private final CartEntityMapper cartEntityMapper;
    private final IStockFeignClient stockFeignClient;

    @Bean
    public ICartPersistencePort cartPersistencePort(){
        return new CartAdapter(cartRepository, cartEntityMapper);
    }

    @Bean
    public IAuthenticationPersistencePort authenticationPersistencePort(){
        return new AuthenticationAdapter();
    }

    @Bean
    public IStockPersistencePort stockPersistencePort(){
        return new StockAdapter(stockFeignClient);
    }

    @Bean
    public ICartServicePort cartServicePort(){
        return new CartUseCases(cartPersistencePort(), authenticationPersistencePort(), stockPersistencePort());
    }
}
