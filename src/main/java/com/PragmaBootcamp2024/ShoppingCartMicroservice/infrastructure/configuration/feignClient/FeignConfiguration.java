package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.feignClient.interceptor.JwtRequestInterceptor;
import feign.Client;
import feign.Logger;
import feign.RequestInterceptor;
import feign.httpclient.ApacheHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public Client feignClient(){
        return new ApacheHttpClient();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new JwtRequestInterceptor();
    }
}
