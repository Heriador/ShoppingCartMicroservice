package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.beanConfiguration;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartDetailsServicePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.*;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.usecases.CartDetailsUseCases;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient.ITransactionFeignClient;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.adapter.*;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.mapper.CartDetailsMapper;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.mapper.CartEntityMapper;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.repository.CartDetailsRepository;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.repository.CartRepository;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.usecases.CartUseCases;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartServicePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient.IStockFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final CartRepository cartRepository;
    private final CartEntityMapper cartEntityMapper;
    private final CartDetailsRepository cartDetailsRepository;
    private final CartDetailsMapper cartDetailsMapper;
    private final IStockFeignClient stockFeignClient;
    private final ITransactionFeignClient transactionFeignClient;

    @Bean
    public ICartPersistencePort cartPersistencePort(){
        return new CartAdapter(cartRepository, cartEntityMapper);
    }

    @Bean
    public ICartDetailsPersistencePort cartDetailsPersistencePort(){
        return new CartDetailsAdapter(cartDetailsRepository, cartDetailsMapper);
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
    public ITransactionPersistencePort transactionPersistencePort(){
        return new TransactionFeignAdapter(transactionFeignClient);
    }

    @Bean
    public ICartDetailsServicePort cartDetailsServicePort(){
        return new CartDetailsUseCases(cartDetailsPersistencePort(), stockPersistencePort(), transactionPersistencePort());
    }

    @Bean
    public ICartServicePort cartServicePort(){
        return new CartUseCases(cartPersistencePort(), authenticationPersistencePort(), cartDetailsServicePort());
    }
}
